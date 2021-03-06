package com.web.blog.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import com.web.blog.dao.AccuseDao;
import com.web.blog.dao.AdminDao;
import com.web.blog.dao.CommentDao;
import com.web.blog.dao.PostDao;
import com.web.blog.dao.UserDao;
import com.web.blog.model.accuse.Accuse;
import com.web.blog.model.accuse.AccuseListResponse;
import com.web.blog.model.accuse.AccuseRequest;
import com.web.blog.model.accuse.AccuseResponse;
import com.web.blog.model.admin.Admin;
import com.web.blog.model.admin.AdminLoginRequest;
import com.web.blog.model.comment.Comment;
import com.web.blog.model.post.Post;
import com.web.blog.model.user.AdminLoginResponse;
import com.web.blog.model.user.User;
import com.web.blog.model.user.UserResponse;
import com.web.blog.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = UserResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = UserResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = UserResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = UserResponse.class) })

//@CrossOrigin(origins = { "*" })
//@RestController
public class AdminController {
//    @Autowired
//    AccuseDao accuseDao;
//
//    @Autowired
//    UserDao userDao;
//
//    @Autowired
//    PostDao postDao;
//
//    @Autowired
//    CommentDao commentDao;
//
//    @Autowired
//    AdminDao adminDao;
//    @Autowired
//    private JwtService jwtService;
//
//    @PostMapping("/admin/login") // SWAGGER UI??? ????????? REQUEST???
//    @ApiOperation(value = "????????? ?????????") // SWAGGER UI??? ????????? ??????
//    public Object login(@RequestBody AdminLoginRequest req) {
//
//        String adminId = req.getAdminId();
//        String password = req.getPassword();
//        System.out.println(adminId  + " " + password);
//        Optional<Admin> adminOpt = adminDao.getAdminByAdminIdAndPassword(adminId, password);
//        System.out.println(adminOpt.get().toString());
//        if (adminOpt.isPresent()) {
//            System.out.println("????????? ??????  : " + adminId);
//            Admin admin = new Admin(adminId, password);
//            String admintoken = jwtService.createAdminLoginToken(admin);
//
//            User usert = userDao.getUserByEmail(adminId);
//
//            User user = new User();
//            user.setNickname(usert.getNickname());
//            user.setEmail(adminId);
//            String token = jwtService.createLoginToken(user);
//            AdminLoginResponse result = new AdminLoginResponse();
//            result.setAdminToken(admintoken);
//            result.setToken(token);
//
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        } else {
//            System.out.println("????????? ??????");
//            return new ResponseEntity<>("????????? ?????? ", HttpStatus.NOT_FOUND);
//        }
//
//    }
//
//
//    @PostMapping("/accuse/create")
//    @ApiOperation(value = "????????? ??????")
//    public Object create(@RequestBody AccuseRequest req) throws MessagingException, IOException {
//
//        String token = req.getToken();
//        User jwtuser = jwtService.getUser(token);
//        Optional<User> userOpt = userDao.findUserByEmail(jwtuser.getEmail());
//        if (userOpt.isPresent()) {
//            Accuse accuse = new Accuse(req.getReporter(), req.getDefendant(), req.getAccuseIndex(),
//                    req.getAccuseValue(), req.getAccuseKind(), req.getAccuseReason(), req.getAccuseUrl(),
//                    req.getAccuseConfirm());
//
//            System.out.println(accuse);
//            accuseDao.save(accuse);
//            System.out.println("????????? ??????!!");
//
//            return new ResponseEntity<>("????????? ??????", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("????????? ????????? ???????????????", HttpStatus.BAD_REQUEST);
//        }
//
//    }
//
//    @PostMapping("/accuse/read")
//    @ApiOperation(value = "????????? ??????")
//    public Object read(@RequestBody AccuseRequest req) throws MessagingException, IOException {
//        String token = req.getToken();
//        Admin jwtadmin = jwtService.getAdmin(token);
//        Optional<Admin> adminOpt = adminDao.findAdminByAdminIdAndPassword(jwtadmin.getAdminId(), jwtadmin.getPassword());
//        if (adminOpt.isPresent()) {
//            System.out.println("????????? ?????? ??????!!");
//
//            List<Accuse> alist = accuseDao.findAccuseByAccuseConfirm(0);
//            AccuseListResponse result = new AccuseListResponse();
//
//            result.accuseList = new LinkedList<>();
//            for (int i = 0; i < alist.size(); i++) {
//                Accuse a = alist.get(i);
//                result.accuseList.add(new AccuseResponse(a.getAccuseId(), a.getReporter(), a.getDefendant(),
//                        a.getAccuseIndex(), a.getAccuseValue(), a.getAccuseKind(), a.getAccuseReason(),
//                        a.getAccuseUrl(), a.getAccuseConfirm()));
//            }
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("???????????? ????????????.", HttpStatus.BAD_REQUEST);
//        }
//
//    }
//
//    @PutMapping("/accuse/applyto")
//    @ApiOperation(value = "?????? ????????????")
//    public Object applyto(@Valid @RequestBody AccuseRequest req) {
//        String token = req.getToken();
//        Admin jwtadmin = jwtService.getAdmin(token);
//        Optional<Admin> adminOpt = adminDao.findAdminByAdminIdAndPassword(jwtadmin.getAdminId(), jwtadmin.getPassword());
//
//        if (adminOpt.isPresent()) {
//            String reporter = req.getReporter();
//            String defendant = req.getDefendant();
//            User userR = userDao.getUserByNickname(reporter);
//            User userD = userDao.getUserByNickname(defendant);
//            // ????????? ???????????? ?????? ??????????????? ?????? ?????? ????????? 1??? ??????????????? ???????????? ????????? ???????????? ????????? ??????
//            if (req.getAccuseConfirm() == 1) {
//                if (req.getAccuseKind() == 1) { // 1. ????????? ????????? ????????? -20
//                    userD.setUserPoint(userD.getUserPoint() - 20);
//                } else if (req.getAccuseKind() == 2) { // 2. ??????, ??????, ?????? -50
//                    userD.setUserPoint(userD.getUserPoint() - 50);
//                } else if (req.getAccuseKind() == 3) { // 3. ?????? ????????????. -30
//                    userD.setUserPoint(userD.getUserPoint() - 30);
//                }
//                // else if (req.getAccuseKind() == 4) { // 4. ?????? - 10
//                //     user.setUserPoint(user.getUserPoint() - 10);
//                // }
//                int up = userD.getUserPoint();
//                if(up <= 1000){
//                    userD.setGrade(1);
//                }else if(up <= 1500){
//                    userD.setGrade(2);
//                }else if(up <= 2500){
//                    userD.setGrade(3);
//                }else if(up <= 4000){
//                    userD.setGrade(4);
//                }else {
//                    userD.setGrade(5);
//                }
//                userDao.save(userD);
//                System.out.println("??????(user point) ??????");
//                Accuse accuse = accuseDao.findAccuseByAccuseId(req.getAccuseId());
//                accuse.setAccuseConfirm(1);
//                accuseDao.save(accuse);
//                System.out.println("?????? ????????? ?????? ??????");
//                // ???????????? ?????? ????????? ???????????? ???????????? ????????????
//                if (req.getAccuseIndex() == 1 || req.getAccuseIndex() == 3 || req.getAccuseIndex() == 5) {
//                    Post post = postDao.getPostByArticleId(req.getAccuseValue());
//                    post.setStatus(0); // ???????????? ???????????? ???????????????
//                    postDao.save(post);
//                } else if (req.getAccuseIndex() == 2 || req.getAccuseIndex() == 4 || req.getAccuseIndex() == 6) {
//                    Comment comment = commentDao.getCommentByCommentId(req.getAccuseValue());
//                    comment.setStatus(0); // ???????????? ????????? ???????????????
//                    commentDao.save(comment);
//                }
//
//            }
//            // ????????? ????????? ??????
//            else if (req.getAccuseConfirm() == 2) {
//                userR.setUserPoint(userR.getUserPoint() - 30);
//                int up = userR.getUserPoint();
//                if(up <= 1000){
//                    userR.setGrade(1);
//                }else if(up <= 1500){
//                    userR.setGrade(2);
//                }else if(up <= 2500){
//                    userR.setGrade(3);
//                }else if(up <= 4000){
//                    userR.setGrade(4);
//                }else {
//                    userR.setGrade(5);
//                }
//                userDao.save(userR);
//                Accuse accuse = accuseDao.findAccuseByAccuseId(req.getAccuseId());
//                accuse.setAccuseConfirm(2);
//                accuseDao.save(accuse);
//                userDao.save(userR);
//                System.out.println("??????(user point) ??????");
//
//            }
//
//            return new ResponseEntity<>("?????? ????????? ?????????????????????.", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("???????????? ????????????.", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PutMapping("/accuse/disabled")
//    @ApiOperation(value = "?????????????????????")
//    public Object disabled(@Valid @RequestBody AccuseRequest req) {
//        String token = req.getToken();
//        Admin jwtadmin = jwtService.getAdmin(token);
//        Optional<Admin> adminOpt = adminDao.findAdminByAdminIdAndPassword(jwtadmin.getAdminId(), jwtadmin.getPassword());
//
//        if (adminOpt.isPresent()) {
//            String nickname = req.getNickname();
//            User user = userDao.getUserByNickname(nickname);
//            user.setStatus(0);
//            userDao.save(user);
//
//            System.out.println("user ???????????????????????????!!!");
//
//            return new ResponseEntity<>("????????? ????????????????????????.", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("???????????? ????????????.", HttpStatus.BAD_REQUEST);
//        }
//    }

}