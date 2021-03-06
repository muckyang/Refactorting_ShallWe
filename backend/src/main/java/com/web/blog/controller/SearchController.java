package com.web.blog.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import javax.mail.MessagingException;

import com.web.blog.dao.CommentDao;
import com.web.blog.dao.LikeDao;
import com.web.blog.dao.ParticipantDao;
// import com.web.blog.dao.LikeDao;
import com.web.blog.dao.PostDao;
import com.web.blog.dao.TagDao;
import com.web.blog.dao.UserDao;
import com.web.blog.model.post.PostListResponse;
import com.web.blog.model.post.PostResponse;
import com.web.blog.model.post.PostSearchRequest;
import com.web.blog.model.tag.Tag;
import com.web.blog.model.comment.Comment;
import com.web.blog.model.comment.CommentRes;
import com.web.blog.model.post.Post;
import com.web.blog.model.user.TokenRequest;
import com.web.blog.model.user.User;
import com.web.blog.service.JwtService;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = PostResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = PostResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = PostResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = PostResponse.class) })

//@CrossOrigin(origins = { "*" })
//@RestController
public class SearchController {
//    @Autowired
//    PostDao postDao;
//
//    @Autowired
//    UserDao userDao;
//
//    @Autowired
//    LikeDao likeDao;
//
//    @Autowired
//    CommentDao commentDao;
//
//    @Autowired
//    TagDao tagDao;
//
//    @Autowired
//    ParticipantDao participantDao;
//
//    @Autowired
//    private JwtService jwtService;
//
//    @PostMapping("/account/readAll")
//    @ApiOperation(value = "?????? ?????? ?????????")
//    public List<User> userReadAll(@RequestBody TokenRequest req) {
//        List<User> result = null;
//        String token = req.getToken();
//        User jwtuser = jwtService.getUser(token);
//        Optional<User> userOpt = userDao.findUserByEmail(jwtuser.getEmail());
//
//        if (userOpt.isPresent() && userOpt.get().getGrade() == 0) {
//            result = userDao.findAll();
//        } else {
//            return null;
//        }
//        return result;
//
//    }
//
//
//    @PostMapping("/post/read/{temp}/{categoryId}/{pageNum}")
//    @ApiOperation(value = "????????? ??? ????????? ??????")
//    public PostListResponse read(@RequestBody TokenRequest req, @PathVariable int temp, @PathVariable int categoryId, @PathVariable int pageNum)
//            throws MessagingException, IOException {
//        PostListResponse result = null;
//        int start = (pageNum*9)+1;
//        int end=start+9;
//
//        if (temp == 0) {
//            System.out.println("????????? ?????? ??????!!");
//            String token = req.getToken();
//            User jwtuser = jwtService.getUser(token);
//            Optional<User> userOpt = userDao.findUserByEmail(jwtuser.getEmail());
//
//            String writer = userOpt.get().getNickname();
//            List<Post> plist = postDao.findPostByTempAndWriterOrderByCreateTimeDesc(temp, writer);
//
//            result = new PostListResponse();
//            result.postList = new LinkedList<>();
//            for (int i = 0; i < plist.size(); i++) {
//                Post p = plist.get(i);
//
//                String tag = p.getTag();
//                StringTokenizer st = new StringTokenizer(tag, "#");
//
//                List<String> taglist = new LinkedList<>();
//                while (!st.hasMoreTokens()) {
//                    taglist.add(st.nextToken());
//                }
//
//                PostResponse presp = new PostResponse();
//
//                presp.articleId = p.getArticleId();
//                presp.categoryId = p.getCategoryId();
//                presp.userId = p.getUserId();
//                presp.title = p.getTitle();
//                presp.address = p.getAddress();
//                presp.minPrice = p.getMinPrice();
//                presp.sumPrice = p.getSumPrice();
//                presp.likeNum = p.getLikeNum();
//                presp.commentNum = p.getCommentNum();
//                presp.description = p.getDescription();
//                presp.writer = p.getWriter();
//                presp.urlLink = p.getUrlLink();
//                presp.openLink = p.getOpenLink();
//                presp.image = p.getImage();
//                presp.tags = taglist;
//                presp.temp = p.getTemp();
//                presp.endTime = p.getEndTime();
//                presp.timeAgo = BeforeCreateTime(p.getCreateTime());
//                presp.createTime = p.getCreateTime();
//
//                result.postList.add(presp);
//
//            }
//            return result;
//        } else if (temp == 1) {
//
//            List<Post> list;
//            List<Post> plist=new ArrayList<>();
//            System.out.println("????????? ?????? ??????!!");
//            long before = System.currentTimeMillis();
//            if (categoryId == 0){// ?????? ????????? ??????
//                list = postDao.findPostByTemp(temp);//??? ?????? ??????
//                for (int i = start; i < end; i++) {
//                    plist.add(list.get(i));//???????????? ?????? ???????????? ????????? ?????????
//                    System.out.println(list.get(i).getArticleId());
//                }
//            }
//            else
//                plist = postDao.findPostByTempAndCategoryId(temp, categoryId);
//
//
//            if (categoryId == 0) {// ?????? ????????? ??????
//                // plist = postDao.findPostByTemp(temp);//??? ?????? ??????
//                list = postDao.findPostByTempAndStatusNotOrderByCreateTimeDesc(temp,0);// ??? ?????? ??????
//                if (list.size() >= start) {
//                    int newend = list.size() - start;
//                    if (newend / scrollsize > 0) {// ????????? 6?????? ??????
//                        for (int i = start; i < end; i++) {
//                            plist.add(list.get(i));// ???????????? ?????? ???????????? ????????? ?????????
//                        }
//                    } else {// ??? ?????? ???????????? ??????
//                        for (int i = start; i < start + newend; i++) {
//                            plist.add(list.get(i));// ???????????? ?????? ???????????? ????????? ?????????
//                        }
//                    }
//                }
//            } else {
//                list = postDao.findPostByTempAndCategoryIdAndStatusNotOrderByCreateTimeDesc(temp, categoryId,0);
//                System.out.println(pageNum + "  " + list.size() + "   " + start + "   " + end + "    >>>>>>>>>>>>>>");
//                if (list.size() >= start) {
//                    int newend = list.size() - start;
//                    if (newend / scrollsize > 0) {// ????????? 6?????? ??????
//                        for (int i = start; i < end; i++) {
//                            plist.add(list.get(i));// ???????????? ?????? ???????????? ????????? ?????????
//                        }
//                    } else {// ??? ?????? ???????????? ??????
//                        for (int i = start; i < start + newend; i++) {
//                            plist.add(list.get(i));// ???????????? ?????? ???????????? ????????? ?????????
//                        }
//                    }
//                }
//            }
//
//            result.postList = getPostList(plist, temp); // ????????? ?????? ??? ??? ???????????? ????????? ?????? ???
//            System.out.println("??????!!" + (System.currentTimeMillis() - before) + "??? ");
//            return result;
//        } else if (temp == 2) { // ???????????????
//            scrollsize = 10;
//            int start = (pageNum * scrollsize);
//            int end = start + scrollsize;
//
//            List<Post> list;
//            List<Post> plist = new ArrayList<>();
//            if (categoryId == 100) {// ?????? ????????? ??????
//                list = postDao.getPostByTempAndCategoryIdNotAndStatusNotOrderByCategoryIdAscCreateTimeDesc(temp, 102,0);
//                if (list.size() >= start) {
//                    int newend = list.size() - start;
//                    if (newend / scrollsize > 0) {// ????????? 10?????? ??????
//                        for (int i = start; i < end; i++) {
//                            plist.add(list.get(i));// ???????????? ?????? ???????????? ????????? ?????????
//                        }
//                    } else {// ??? ?????? ???????????? ??????
//                        for (int i = start; i < start + newend; i++) {
//                            plist.add(list.get(i));// ???????????? ?????? ???????????? ????????? ?????????
//                        }
//                    }
//                }
//            } else {
//                list = postDao.findPostByTempAndCategoryIdAndStatusNotOrderByCreateTimeDesc(temp, categoryId,0);
//                System.out.println(pageNum + "  " + list.size() + "   " + start + "   " + end + "    >>>>>>>>>>>>>>");
//                if (list.size() >= start) {
//                    int newend = list.size() - start;
//                    if (newend / scrollsize > 0) {// ????????? 6?????? ??????
//                        for (int i = start; i < end; i++) {
//                            plist.add(list.get(i));// ???????????? ?????? ???????????? ????????? ?????????
//                        }
//                    } else {// ??? ?????? ???????????? ??????
//                        for (int i = start; i < start + newend; i++) {
//                            plist.add(list.get(i));// ???????????? ?????? ???????????? ????????? ?????????
//                        }
//                    }
//                }
//            }
//
//            result.postList = getPostList(plist, temp); // ????????? ?????? ??? ??? ???????????? ????????? ?????? ???
//            if (categoryId == 102) {
//                for (int i = 0; i < plist.size(); i++)
//                    CommentListAdd(result, i, plist.get(i));
//
//            }
//
//            System.out.println("????????? ?????? ??????!!");
//            return result;
//        } else if (temp == 3) {
//            System.out.println("??? ?????? ????????? ?????? ??????!!");
//            String token = req.getToken();
//            User jwtuser = jwtService.getUser(token);
//            Optional<User> userOpt = userDao.findUserByEmail(jwtuser.getEmail());
//
//            String uAddress = userOpt.get().getAddress();
//            int count = 0;
//            List<String> addList = new ArrayList<>();
//
//            StringTokenizer st = new StringTokenizer(uAddress);
//            // ????????? ????????? ?????????
//            while (st.hasMoreTokens()) {
//                if (count == 3) {
//                    break;
//                }
//                count++;
//                addList.add("%" + st.nextToken() + "%");
//            }
//
//            scrollsize = 6;
//            int start = (pageNum * scrollsize);
//            int end = start + scrollsize;
//            List<Post> locallist;
//            List<Post> list = new ArrayList<>();
//            List<Post> plist = new ArrayList<>();
//            locallist = postDao.findPostByStatusNotAndAddressLikeAndAddressLikeAndAddressLikeOrderByCreateTimeDesc(0,addList.get(0),
//                    addList.get(1), addList.get(2));
//
//            for (int i = 0; i < locallist.size(); i++) {// ??????????????? ?????? ?????????????????? ????????????
//                if (locallist.get(i).getTemp() == 1
//                        && (locallist.get(i).getStatus() == 1 || locallist.get(i).getStatus() == 2))// ???????????????????????? status???
//                                                                                                    // 1??????2?????????
//                    list.add(locallist.get(i));
//            }
//
//            if (list.size() >= start) {
//                int newend = list.size() - start;
//                if (newend / scrollsize > 0) {// ????????? 6?????? ??????
//                    for (int i = start; i < end; i++) {
//                        plist.add(list.get(i));// ???????????? ?????? ???????????? ????????? ?????????
//                        System.out.println(list.get(i).getArticleId());
//                    }
//                } else {// ??? ?????? ???????????? ??????
//                    for (int i = start; i < start + newend; i++) {
//                        plist.add(list.get(i));// ???????????? ?????? ???????????? ????????? ?????????
//                        System.out.println(list.get(i).getArticleId());
//                    }
//                }
//            }
//
//            result.postList = getPostList(plist, temp);
//
//            System.out.println("??? ?????? ????????? ?????? ??????");
//            return result;
//        } else {
//            System.out.println("qwewqeqw");
//            return result;
//        }
//    }
//
//    @PostMapping("/post/search/{temp}/{categoryId}")
//    @ApiOperation(value = "??????????????? ?????? ??????")
//    public Object search(@RequestBody PostSearchRequest request, @PathVariable int temp, @PathVariable int categoryId)
//            throws MessagingException, IOException {
//
//        String subject = request.getSubject();
//        String word = request.getWord();
//        List<Post> plist = new LinkedList<>();
//        if (subject.equals("headertitle")) {
//            word = "%" + word + "%";
//            System.out.println("???????????? ??????, ??????????????? title??? ??????");
//
//            // ????????? temp??? ???????????? ???????????? ???
//            plist = postDao.findPostByStatusNotAndTitleLikeOrderByCreateTimeDesc(0,word);
//            PostListResponse result = new PostListResponse();
//            result.postList = getPostList(plist, temp);
//
//            System.out.println("???????????? ??????, ??????????????? title??? ?????? ??????");
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        } else if (subject.equals("title")) {
//            word = "%" + word + "%";
//            System.out.println("title??? ??????");
//
//            if (categoryId == 0)
//                plist = postDao.findPostByStatusNotAndTempAndTitleLikeOrderByCreateTimeDesc(0,temp, word);
//            else
//                plist = postDao.findPostByStatusNotAndTempAndCategoryIdAndTitleLikeOrderByCreateTimeDesc(0,temp, categoryId, word);
//            PostListResponse result = new PostListResponse();
//
//            result.postList = getPostList(plist, temp);
//
//            System.out.println("title??? ?????? ??????");
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        } else if (subject.equals("writer")) {
//            word = "%" + word + "%";
//
//            if (categoryId == 0)
//                plist = postDao.findPostByStatusNotAndTempAndWriterLikeOrderByCreateTimeDesc(0,temp, word);
//            else
//                plist = postDao.findPostByStatusNotAndTempAndCategoryIdAndWriterLikeOrderByCreateTimeDesc(0,temp, categoryId, word);
//            PostListResponse result = new PostListResponse();
//            result.postList = getPostList(plist, temp);
//
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        } else if (subject.equals("tag")) {
//
//            List<Tag> taglist = tagDao.findTagByName(word);// ?????? ????????? tagId?????????
//            PostListResponse result = new PostListResponse();
//            for (int i = 0; i < taglist.size(); i++) {
//                int aId = taglist.get(i).getArticleId();
//                Optional<Post> article = postDao.findPostByAndArticleIdAndTempOrderByCreateTimeDesc(aId, temp);
//                plist.add(article.get());
//                // result.postList.add(new PostResponse(p.getArticleId(), p.getCategoryId(),
//                // p.getUserId(), p.getTitle(),
//                // p.getAddress(), p.getMinPrice(), p.getSumPrice(), p.getDescription(),
//                // p.getWriter(),
//                // p.getUrlLink(), p.getImage(), p.getTemp(), p.getEndTime()));
//
//                // List<Like> llist = likeDao.findLikeByArticleId(p.getArticleId());
//                // List<Comment> clist = commentDao.findCommentByArticleId(p.getArticleId());
//                // int likenum = llist.size();
//                // int commentnum = clist.size();
//                // System.out.println("list return success");
//                // System.out.println(likenum);
//
//                // result.postList.get(i).likenum = likenum;
//                // result.postList.get(i).commentnum = commentnum;
//            }
//
//            result.postList = getPostList(plist, temp);
//
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        } else if (subject.equals("address")) {
//
//            System.out.println("address??? ??????");
//            List<String> addList = new ArrayList<>();
//
//            StringTokenizer st = new StringTokenizer(word);
//            while (st.hasMoreTokens()) {
//                addList.add("%" + st.nextToken() + "%");
//            }
//
//            if (categoryId == 0) {
//                if (addList.size() == 1) {
//                    plist = postDao.findPostByStatusNotAndAddressLikeOrderByCreateTimeDesc(0,addList.get(0));
//                } else if (addList.size() == 2) {
//                    plist = postDao.findPostByStatusNotAndAddressLikeAndAddressLikeOrderByCreateTimeDesc(0,addList.get(0),
//                            addList.get(1));
//                } else if (addList.size() == 3) {
//                    plist = postDao.findPostByStatusNotAndAddressLikeAndAddressLikeAndAddressLikeOrderByCreateTimeDesc(0,
//                            addList.get(0), addList.get(1), addList.get(2));
//                } else if (addList.size() == 4) {
//                    plist = postDao
//                            .findPostByStatusNotAndAddressLikeAndAddressLikeAndAddressLikeAndAddressLikeOrderByCreateTimeDesc(0,
//                                    addList.get(0), addList.get(1), addList.get(2), addList.get(3));
//                }
//
//            } else {
//                if (addList.size() == 1) {
//                    plist = postDao.findPostByStatusNotAndTempAndCategoryIdAndAddressLikeOrderByCreateTimeDesc(0,temp, categoryId,
//                            addList.get(0));
//                } else if (addList.size() == 2) {
//                    plist = postDao.findPostByStatusNotAndTempAndCategoryIdAndAddressLikeAndAddressLikeOrderByCreateTimeDesc(0,temp,
//                            categoryId, addList.get(0), addList.get(1));
//                } else if (addList.size() == 3) {
//                    plist = postDao
//                            .findPostByStatusNotAndTempAndCategoryIdAndAddressLikeAndAddressLikeAndAddressLikeOrderByCreateTimeDesc(
//                                0,temp, categoryId, addList.get(0), addList.get(1), addList.get(2));
//                } else if (addList.size() == 4) {
//                    plist = postDao
//                            .findPostByStatusNotAndTempAndCategoryIdAndAddressLikeAndAddressLikeAndAddressLikeAndAddressLikeOrderByCreateTimeDesc(
//                                   0, temp, categoryId, addList.get(0), addList.get(1), addList.get(2), addList.get(3));
//                }
//            }
//            PostListResponse result = new PostListResponse();
//            result.postList = getPostList(plist, temp);
//
//            System.out.println("address??? ?????? ??????");
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("temp ?????? ??????", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    private List<PostResponse> getPostList(List<Post> plist, int temp) {
//        List<PostResponse> result = new LinkedList<>();
//
//        for (int i = 0; i < plist.size(); i++) { // ??? ????????? ?????? ????????? ??? ????????????
//            Post p = plist.get(i);
//
//            List<String> taglist = new LinkedList<>();
//            String tag = p.getTag();
//            if (tag != null) {
//                StringTokenizer st = new StringTokenizer(tag, "#");
//                while (st.hasMoreTokens()) {
//                    taglist.add(st.nextToken());
//                }
//            }
//
//            PostResponse presp = new PostResponse();
//
//            presp.articleId = p.getArticleId();
//            presp.categoryId = p.getCategoryId();
//            presp.userId = p.getUserId();
//            presp.title = p.getTitle();
//            presp.status = p.getStatus();
//            presp.address = p.getAddress();
//            presp.minPrice = p.getMinPrice();
//            presp.sumPrice = p.getSumPrice();
//            presp.likeNum = p.getLikeNum();
//            presp.commentNum = p.getCommentNum();
//            presp.description = p.getDescription();
//            presp.writer = p.getWriter();
//            presp.urlLink = p.getUrlLink();
//            presp.openLink = p.getOpenLink();
//            presp.image = p.getImage();
//            presp.tags = taglist;
//            presp.temp = p.getTemp();
//            presp.endTime = p.getEndTime();
//            presp.timeAgo = BeforeCreateTime(p.getCreateTime());
//            presp.createTime = p.getCreateTime();
//
//            result.add(presp);
//        }
//
//        return result;
//    }
//
//    private void CommentListAdd(PostListResponse result, int index, Post p) {
//
//        List<Comment> clist = commentDao.findCommentByArticleId(p.getArticleId());
//        List<CommentRes> crlist = new ArrayList<>();
//        for (int j = 0; j < clist.size(); j++) {
//            CommentRes cr = new CommentRes();
//            Comment c = clist.get(j);
//            cr.setArticleId(c.getArticleId());
//            cr.setCommentId(c.getCommentId());
//            cr.setUserId(c.getUserId());
//            cr.setContent(c.getContent());
//            cr.setNickname(c.getWriter());
//            cr.setStatus(c.getStatus());
//            cr.setTimeAgo(BeforeCreateTime(c.getCreateTime()));
//            cr.setCreateTime(c.getCreateTime());
//            crlist.add(cr);
//        }
//        result.postList.get(index).commentList = crlist;
//        return;
//
//    }
//
//    private static String BeforeCreateTime(LocalDateTime createTime) {
//        String result = "";
//        int before = 0;
//        LocalDateTime nowTime = LocalDateTime.now();
//        if (createTime.getYear() <= nowTime.getYear() + 1) {
//            if (createTime.getMonthValue() == nowTime.getMonthValue()) {
//                if (createTime.getDayOfYear() == nowTime.getDayOfYear()) {
//                    if (createTime.getHour() == nowTime.getHour()) {
//                        if (createTime.getMinute() == nowTime.getMinute()) {
//                            result = "??? 1??? ???";
//                        } else {
//                            before = nowTime.getMinute() - createTime.getMinute();
//                            result = "??? " + before + "??? ???";
//                        }
//                    } else if (createTime.getHour() == nowTime.getHour() + 1
//                            && createTime.getMinute() > nowTime.getMinute()) {
//                        before = 60 + nowTime.getMinute() - createTime.getMinute();
//                        result = "??? " + before + "??? ???";
//                    } else {
//                        before = nowTime.getHour() - createTime.getHour();
//                        result = "??? " + before + "?????? ???";
//                    }
//                } else if (createTime.getDayOfYear() == nowTime.getDayOfYear() + 1
//                        && createTime.getHour() > nowTime.getHour()) {
//                    before = 24 + nowTime.getHour() - createTime.getHour();
//                    result = "??? " + before + "?????? ???";
//                } else {
//                    before = nowTime.getDayOfYear() - createTime.getDayOfYear();
//                    result = "??? " + before + "??? ???";
//                }
//            } else if (createTime.getDayOfYear() == nowTime.getDayOfYear() + 1
//                    && createTime.getHour() > nowTime.getHour()) {
//                before = 24 + nowTime.getHour() - createTime.getHour();
//                result = "??? " + before + "??? ???";
//            } else {
//                before = nowTime.getDayOfYear() - createTime.getDayOfYear();
//                result = "??? " + before + "??? ???";
//            }
//        } else {
//            before = nowTime.getYear() - createTime.getYear();
//
//            result = "??? " + before + "??? ???";
//        }
//        return result;
//
//    }
}