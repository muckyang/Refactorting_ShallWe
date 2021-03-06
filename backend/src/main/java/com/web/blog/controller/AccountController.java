package com.web.blog.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import com.web.blog.dao.PostDao;
import com.web.blog.dao.CommentDao;
import com.web.blog.dao.LikeDao;
import com.web.blog.dao.ParticipantDao;
import com.web.blog.dao.UserDao;
import com.web.blog.model.user.UserResponse;
import com.web.blog.model.comment.Comment;
import com.web.blog.model.like.Like;
import com.web.blog.model.participant.Participant;
import com.web.blog.model.post.Post;
import com.web.blog.model.user.KsignupRequest;
import com.web.blog.model.user.TokenRequest;
import com.web.blog.model.user.User;
import com.web.blog.model.user.UserRequest;
import com.web.blog.service.JwtService;
import com.web.blog.service.KakaoService;
import com.web.blog.service.KakaoUserInfo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = UserResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = UserResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = UserResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = UserResponse.class) })

//@CrossOrigin(origins = { "*" })
//@RestController
public class AccountController {

//    @Autowired
    UserDao userDao;
    @Autowired
    PostDao postDao;
    @Autowired
    CommentDao commentDao;
    @Autowired
    LikeDao likeDao;

    @Autowired
    ParticipantDao partDao;

    @Autowired
    private JwtService jwtService;
    @Autowired
    KakaoService kakao;

    @Autowired
    KakaoUserInfo kakaoUserInfo;

    @RequestMapping("/account/kakaoLogin")
    @ApiOperation(value = "????????? ?????????") // SWAGGER UI??? ????????? ??????
    public Object kakaoLogin(@RequestParam("code") String code) throws URISyntaxException {

        String access_Token = "";
        try {
            access_Token = kakao.getAccessToken(code);
            System.out.println("controller access_token : " + access_Token);
            System.out.println(kakao.getUserInfo(access_Token));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("????????? ????????? ?????? : " + code);

        HashMap<String, Object> userInfo = null;
        userInfo = kakao.getUserInfo(access_Token);

        RedirectView redirectView = new RedirectView();
        String kemail = userInfo.get("email").toString();

        Optional<User> user = userDao.findUserByEmail(kemail);
        System.out.println(user.toString());
        if (user.isPresent()) { // ?????? ????????? ?????????
            System.out.println("?????? ????????? ??????????????????.");
            User tuser = new User(user.get().getEmail(), user.get().getNickname());

            String token = jwtService.createLoginToken(tuser);
            // redirectView.setUrl("http://localhost:8081/user/klogin");// ????????? ????????? ?????????
            redirectView.setUrl("http://i3b203.p.ssafy.io/user/klogin");// ????????? ????????? ?????????
            redirectView.addStaticAttribute("token", token);
        } else {// ????????? ?????????
            // redirectView.setUrl("http://localhost:8081/user/join");// ????????? ????????? ?????????
            redirectView.setUrl("http://i3b203.p.ssafy.io/user/join"); // ???????????? ????????? ??????
            redirectView.addStaticAttribute("kemail", kemail);
        }
        return redirectView;

    }

    @Transactional(readOnly = true)
    @GetMapping("/account/nicknamecheck/{nickname}")
    @ApiOperation(value = "????????? ????????????")
    public Object nicknamecheck(@PathVariable String nickname) {
        Optional<User> userOpt = userDao.findUserByNickname(nickname);
        if (userOpt.isPresent()) {
            return new ResponseEntity<>("?????? ???????????? ??????????????????.", HttpStatus.OK);
        }
        return new ResponseEntity<>("??????????????? ??????????????????.", HttpStatus.OK);
    }

    @PostMapping("/account/signup")
    @ApiOperation(value = "????????????")
    public Object signup(@Valid @RequestBody KsignupRequest req) throws MessagingException, IOException {

        User user = new User();
        user.setEmail(req.getEmail());
        user.setGrade(1);
        user.setUserPoint(1000);
        user.setAddress(req.getAddress());
        user.setStatus(1);// ????????? 1??? ??????
        user.setNickname(req.getNickname());
        userDao.save(user);

        User tuser = new User(user.getEmail(), user.getNickname());

        String token = jwtService.createLoginToken(tuser);
        System.out.println("???????????? ??????!");
        return new ResponseEntity<>(token, HttpStatus.OK);

    }

    @Transactional(readOnly = true)
    @GetMapping("/account/read") // SWAGGER UI??? ????????? REQUEST???
    @ApiOperation(value = "??? ????????? ??????")
    public Object info(@RequestBody TokenRequest req) {
        String token = req.getToken();
        ResponseEntity<Object> response = null;
        User jwtuser = jwtService.getUser(token);
        Optional<User> userOpt = userDao.findUserByEmail(jwtuser.getEmail());

        if (userOpt.isPresent()) {
            System.out.println("??? ????????? ?????? ! ");
            User user = userOpt.get();
            int userId = user.getUserId();
            UserResponse result = new UserResponse();
            result.userId = userOpt.get().getUserId();
            result.address = userOpt.get().getAddress();
            result.nickname = userOpt.get().getNickname();
            result.email = userOpt.get().getEmail();
            result.userPoint = userOpt.get().getUserPoint();
            result.grade = userOpt.get().getGrade();
            result.introduce = userOpt.get().getIntroduce();

            result.articleList = new LinkedList<>();
            result.reviewList = new LinkedList<>();
            result.tempList = new LinkedList<>();
            result.freeList = new LinkedList<>();

            List<Post> plist = postDao.findPostByUserId(userId);

            for (int i = 0; i < plist.size(); i++) {
                Post p = plist.get(i);
                if (p.getTemp() == 1) {
                    result.articleList.add(p);
                } else if (p.getTemp() == 0) {
                    result.tempList.add(p);
                } else {
                    if (p.getCategoryId() == 103) {
                        result.freeList.add(p);
                    } else if (p.getCategoryId() == 102) {
                        result.reviewList.add(p);
                    }
                }
            }
            List<Like> llist = likeDao.findLikeByUserId(userId);
            result.likeList = new LinkedList<>();
            for (int i = 0; i < llist.size(); i++) {
                result.likeList.add(postDao.findPostByArticleId(llist.get(i).getArticleId()));
            }

            result.completeList = new LinkedList<>();
            result.joinList = new LinkedList<>();
            List<Participant> partlist = partDao.getParticipantByUserIdAndStatus(userId, 1);// ?????? ????????? ??? ???
            for (int i = 0; i < partlist.size(); i++) {
                Post p = postDao.getPostByArticleId(partlist.get(i).getArticleId());
                if (p.getStatus() == 4)
                    result.completeList.add(p);
                else if (p.getStatus() == 3 || p.getStatus() == 2 || p.getStatus() == 1) {
                    result.joinList.add(p);
                }
            }

            result.articleCount = result.articleList.size();
            result.reviewCount = result.reviewList.size();
            result.likeCount = result.likeList.size();
            result.tempCount = result.tempList.size();
            result.joinCount = result.joinList.size();
            result.completeCount = result.completeList.size();
            result.freeCount = result.freeList.size();

            System.out.println("??? ????????? ?????? !!! ");
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Transactional(readOnly = true)
    @PostMapping("/account/read/{userId}") // SWAGGER UI??? ????????? REQUEST???
    @ApiOperation(value = "????????? ????????? ??????")
    public Object infoUser(@RequestBody TokenRequest req, @PathVariable int userId) {
        String token = req.getToken();
        ResponseEntity<Object> response = null;
        User jwtuser = jwtService.getUser(token);
        Optional<User> userOpt = userDao.findUserByEmail(jwtuser.getEmail());
        Optional<User> profileOpt = userDao.findUserByUserId(userId);

        if (userOpt.isPresent() && profileOpt.isPresent()) {
            System.out.println("????????? ????????? ?????? ! ");
            User user = profileOpt.get();
            UserResponse result = new UserResponse();
            result.userId = user.getUserId();
            result.address = user.getAddress();
            result.nickname = user.getNickname();
            result.email = user.getEmail();
            result.userPoint = user.getUserPoint();
            result.grade = user.getGrade();

            System.out.println("?????? ????????? ?????? !!! ");
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PutMapping("/account/update")
    @ApiOperation(value = "????????????")
    public Object update(@Valid @RequestBody UserRequest req) {
        String token = req.getToken();

        // ?????????' ;
        User jwtuser = jwtService.getUser(token);
        Optional<User> userOpt = userDao.findUserByEmail(jwtuser.getEmail());
        String message = "";

        System.out.println("????????????");
        if (userOpt.isPresent()) {
            User user = userDao.getUserByEmail(jwtuser.getEmail());
            user.setAddress(req.getAddress());
            // user.setNickname(req.getNickname());

            user.setIntroduce(req.getIntroduce());
            user.setBirthday(req.getBirthday());

            // if (user.getNickname() != req.getNickname()) {

            //     // ????????? ????????? ?????? ?????????
            //     List<Participant> palist = partDao.getParticipantByUserId(user.getUserId());
            //     for (int i = 0; i < palist.size(); i++) {
            //         Participant p = palist.get(i);
            //         p.setWriter(req.getNickname());
            //         partDao.save(p);
            //     }
            //     List<Post> plist = postDao.getPostByUserId(user.getUserId());
            //     for (int i = 0; i < plist.size(); i++) {
            //         Post p = plist.get(i);
            //         p.setWriter(req.getNickname());
            //         postDao.save(p);
            //     }
            //     List<Comment> clist = commentDao.getCommentByUserId(user.getUserId());
            //     for (int i = 0; i < clist.size(); i++) {
            //         Comment c = clist.get(i);
            //         c.setWriter(req.getNickname());
            //         commentDao.save(c);
            //     }

            // }
            userDao.save(user); // ???????????? ??????
            System.out.println("???????????? ??????!! ");
            User result = user;
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            message = "????????? ??? ????????? ????????????.";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/account/delete")
    @ApiOperation(value = "????????????")
    public Object delete(@RequestBody TokenRequest request) {
        String token = request.getToken();
        User jwtuser = jwtService.getUser(token);

        Optional<User> userOpt = userDao.findUserByEmail(jwtuser.getEmail());
        String message = "";
        if (userOpt.isPresent()) {
            User user = userDao.getUserByEmail(jwtuser.getEmail());

            // FK ???????????? ?????? /// ??????????????? ????????? ??? ??? ??????
            // 1. ??????????????? ???????????? ????????? ??? (??????????????? alert ??????)
            // 2. like_tabel, comment ??????????????? ?????? id ??????
            // 3. article ??????????????? ??????????????? ????????? ??????

            userDao.delete(user);

            System.out.println("?????? ?????? ?????? ");
            message = "?????? ?????????????????????.";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message = "????????? ??? ???????????? ????????????.";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

}
