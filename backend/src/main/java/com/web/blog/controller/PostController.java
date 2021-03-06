package com.web.blog.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import javax.validation.Valid;
import com.web.blog.dao.CommentDao;
import com.web.blog.dao.LikeDao;
import com.web.blog.dao.ParticipantDao;
import com.web.blog.dao.PostDao;
import com.web.blog.dao.TagDao;
import com.web.blog.dao.UserDao;
import com.web.blog.model.post.PostResponse;
import com.web.blog.model.tag.Tag;
import com.web.blog.model.like.Like;
import com.web.blog.model.participant.Participant;
import com.web.blog.model.post.PostRequest;
import com.web.blog.model.post.Post;
import com.web.blog.model.user.TokenRequest;
import com.web.blog.model.user.User;
import com.web.blog.service.JwtService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;

import com.web.blog.model.comment.Comment;
import com.web.blog.model.comment.CommentRes;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = PostResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = PostResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = PostResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = PostResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
public class PostController {
   @Autowired
   PostDao postDao;

   @Autowired
   UserDao userDao;

   @Autowired
   LikeDao likeDao;

   @Autowired
   CommentDao commentDao;

   @Autowired
   TagDao tagDao;

   @Autowired
   ParticipantDao participantDao;

   @Autowired
   private JwtService jwtService;

   // ????????? ???????????? ????????? ?????? ????????????
   @Scheduled(cron = "*/30 * * * * *")
   public void articleTimeOut() {
       System.out.println("30????????? ?????????.");
       List<Post> plist = postDao.findAll();
       for (Post p : plist) {
           if (p.getStatus() == 1 || p.getStatus() == 2) {
               if (p.getTemp() == 1 && datetimeTosec(p.getEndTime()) < datetimeTosec(LocalDateTime.now())) {
                   p.setStatus(5);// ??????
                   postDao.save(p);
               }
           }
       }
   }

   @PostMapping("/post/create/{temp}")
   @ApiOperation(value = "????????? ??? ????????? ??????")
   public Object create(@Valid @RequestBody PostRequest req, @PathVariable int temp) throws IOException {
       String token = req.getToken();
       String endDate = req.getEndDate();
       String endT = req.getEndTime();
       LocalDateTime endTime = null;
       if (endDate != null || endT != null) {
           endTime = LocalDateTime.parse(endDate + " " + endT, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
       }
       System.out.println(token);
       User jwtuser = jwtService.getUser(token);
       System.out.println(jwtuser.toString());
       Optional<User> userOpt = userDao.findUserByEmail(jwtuser.getEmail());
       if (userOpt.isPresent()) {
           if (temp == 0) {// ????????????
               Post post = new Post();
               post.setCategoryId(req.getCategoryId());
               post.setUserId(userOpt.get().getUserId());
               post.setTitle(req.getTitle());
               post.setWriter(userOpt.get().getNickname());
               post.setAddress(req.getAddress());
               post.setDescription(req.getDescription());
               post.setMinPrice(req.getMinPrice());
               post.setSumPrice(req.getMyPrice());
               post.setImage(req.getImage());
               post.setUrlLink(req.getUrlLink());
               post.setOpenLink(req.getOpenLink());
               post.setTemp(temp);
               post.setEndTime(endTime);
               post.setStatus(1);
               post.setLikeNum(0);
               post.setCommentNum(0);
               post.setUrlLink(req.getUrlLink());
               postDao.save(post);

               System.out.println("????????????!!");

               return new ResponseEntity<>("???????????? ??????", HttpStatus.OK);
           } else if (temp == 1) {
               Post post = new Post();
               post.setCategoryId(req.getCategoryId());
               post.setUserId(userOpt.get().getUserId());
               post.setTitle(req.getTitle());
               post.setWriter(userOpt.get().getNickname());
               post.setAddress(req.getAddress());
               post.setDescription(req.getDescription());
               post.setMinPrice(req.getMinPrice());
               post.setSumPrice(req.getMyPrice());
               post.setUrlLink(req.getUrlLink());
               post.setOpenLink(req.getOpenLink());
               post.setImage(req.getImage());
               post.setTemp(temp);
               post.setEndTime(endTime);
               post.setStatus(1);
               post.setLikeNum(0);
               post.setCommentNum(0);

               // ?????? ??????
               String[] tags = req.getTags();// ?????? ??????
               if (tags.length == 0) {
                   post.setTag("??????");
               } else {
                   String ptag = "#";
                   for (int i = 0; i < tags.length; i++) {
                       ptag += tags[i] + "#";

                   }
                   post.setTag(ptag.substring(0, ptag.length() - 1));
               }
               postDao.save(post);
               int artiId = post.getArticleId();
               System.out.println("????????? ??????!!");

               // ????????? ????????? ????????? ????????? ????????????
               int myPrice = req.getMyPrice();
               if (myPrice <= 0) {
                   String message = "price ?????? ???????????? ????????????.";
                   return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
               }
               String def_mes = "????????? ???????????????.";
               Participant participant = new Participant();
               participant.setUserId(userOpt.get().getUserId()); // token????????? id ?????????
               participant.setArticleId(artiId);

               System.out.println(post.getTitle() + " " + artiId);
               participant.setTitle(def_mes);
               participant.setPrice(myPrice);
               participant.setWriter(userOpt.get().getNickname());
               participant.setStatus(1);// ????????? ????????? ????????? ??????
               participant.setDescription(def_mes);
               participantDao.save(participant);// ????????? DB??? ?????? ??????
               tagAdd(tags, artiId);

               System.out.println("????????? ??????!!");

               return new ResponseEntity<>("?????? ?????? ??? ????????? ?????? ??? ????????? ??????", HttpStatus.OK);

           } else if (temp == 2) { // ???????????????
               Post post = new Post();
               post.setUserId(userOpt.get().getUserId());
               post.setCategoryId(req.getCategoryId());
               post.setTitle(req.getTitle());
               post.setWriter(userOpt.get().getNickname());
               post.setDescription(req.getDescription());
               post.setImage(req.getImage());
               post.setStatus(1);
               post.setTemp(temp);
               String ptag = "#?????????";

               if (req.getCategoryId() == 101) {
                   ptag = "#?????????";
               } else if (req.getCategoryId() == 102) {
                   ptag = "#????????? ";
               } else if (req.getCategoryId() == 103) {
                   ptag = "#???????????????";
               }

               post.setTag(ptag.substring(0, ptag.length() - 1));

               postDao.save(post);

               return new ResponseEntity<>("??????????????? ??????", HttpStatus.OK);
           } else {
               return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
           }
       } else {
           return new ResponseEntity<>("????????? ????????? ??????????????? ", HttpStatus.BAD_REQUEST);

       }
   }

   @Transactional(readOnly = true)
   @PostMapping("/post/detail/{articleId}") // SWAGGER UI??? ????????? REQUEST???
   @ApiOperation(value = "?????????????????????") // SWAGGER UI??? ????????? ??????
   public Object detail(@PathVariable int articleId, @RequestBody TokenRequest request) {
       // ?????? ???????????? ??? ???????????? ????????? ?????? ??? uid ???????????? ????????? ?????? ?????? ??????
       long before = System.currentTimeMillis();

       // System.out.println("???????????? ????????? " + request.toString());
       Post p = postDao.findPostByArticleId(articleId);

       if (p != null) {
           String token = request.getToken();
           User jwtuser = jwtService.getUser(token);
           System.out.println(jwtuser.toString());
           String tag = p.getTag();
           StringTokenizer st = new StringTokenizer(tag, "#");

           List<String> taglist = new LinkedList<>();
           while (!st.hasMoreTokens()) {
               taglist.add(st.nextToken());
           }

           Optional<User> userOpt = userDao.findUserByEmail(jwtuser.getEmail());

           PostResponse result = new PostResponse();

           result.articleId = p.getArticleId();
           result.categoryId = p.getCategoryId();
           result.userId = p.getUserId();
           result.title = p.getTitle();
           result.address = p.getAddress();
           result.minPrice = p.getMinPrice();
           result.sumPrice = p.getSumPrice();
           result.likeNum = p.getLikeNum();
           result.commentNum = p.getCommentNum();
           result.description = p.getDescription();
           result.writer = p.getWriter();
           result.urlLink = p.getUrlLink();
           result.openLink = p.getOpenLink();
           result.image = p.getImage();
           result.tags = taglist;
           result.temp = p.getTemp();
           result.endTime = p.getEndTime();
           result.timeAgo = BeforeCreateTime(p.getCreateTime());
           result.createTime = p.getCreateTime();

           result.status = p.getStatus();
           // ??? ???????????? ???????????? ????????? ??? ?????????
           List<Tag> tlist = tagDao.findTagByArticleId(articleId);
           List<String> tags = new LinkedList<>();
           for (int i = 0; i < tlist.size(); i++) {
               tags.add(tlist.get(i).getName());
           }
           List<Participant> partlist = participantDao.findParticipantByArticleId(articleId);
           result.partList = partlist;
           result.tags = tags;
           result.nameList = new LinkedList<>();
           result.scoreList = new LinkedList<>();
           result.rgbCodeList = new LinkedList<>();
           int sum = 0;
           for (int i = 0; i < partlist.size(); i++) {
               if (partlist.get(i).getStatus() == 1) {
                   sum += partlist.get(i).getPrice();
                   result.nameList.add(partlist.get(i).getWriter());
                   result.scoreList.add(partlist.get(i).getPrice());
                   if (i == 0) {
                       result.rgbCodeList.add("#FF6A89");
                   } else if (i == 1) {
                       result.rgbCodeList.add("#50B4FF");
                   } else if (i == 2) {
                       result.rgbCodeList.add("#FFE13C");
                   } else if (i == 3) {
                       result.rgbCodeList.add("#93FB93");
                   } else if (i == 4) {
                       result.rgbCodeList.add("#DD78F6");
                   }
               }
           }
           if (sum >= p.getMinPrice()) {// ?????? ?????? ??????

           } else {
               result.nameList.add("????????????");
               result.scoreList.add(p.getMinPrice() - sum);
               result.rgbCodeList.add("#ADADAD");
           }
           if (userOpt.isPresent()) {// ????????? ????????????

               Optional<Like> isILiked = likeDao.findLikeByUserIdAndArticleId(userOpt.get().getUserId(), articleId);
               if (isILiked.isPresent()) // ????????? ??? ??????
                   result.isLiked = true;
               else // ????????? ?????? ????????????
                   result.isLiked = false;

               System.out.println("????????? ???????????? !!!");
           } else {
               System.out.println("???????????? / ????????? ?????? ?????? !!!");
               result.isLiked = false;
           }

           List<Comment> clist = commentDao.findCommentByArticleId(p.getArticleId());
           result.commentList = new LinkedList<>(); // ?????? ????????? ?????????
           for (int i = 0; i < clist.size(); i++) {
               User user = userDao.getUserByUserId(clist.get(i).getUserId());
               String nickname = user.getNickname();
               CommentRes c = new CommentRes();
               c.setCommentId(clist.get(i).getCommentId());
               c.setArticleId(clist.get(i).getArticleId());
               c.setUserId(clist.get(i).getUserId());

               c.setStatus(clist.get(i).getStatus());
               if (clist.get(i).getStatus() == 0) {
                   c.setNickname("????????? ??????");
                   c.setContent("????????? ?????? ?????????.");
               } else {
                   c.setNickname(nickname);
                   c.setContent(clist.get(i).getContent());
               }
               c.setTimeAgo(BeforeCreateTime(clist.get(i).getCreateTime()));
               c.setCreateTime(clist.get(i).getCreateTime());

               result.commentList.add(c);

               System.out.println(nickname);
           }

           System.out.println("??????!!" + (System.currentTimeMillis() - before) + "??? ");

           return new ResponseEntity<>(result, HttpStatus.OK);
       } else {
           return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       }
   }

   @PutMapping("/post/update/{temp}")
   @ApiOperation(value = "????????? ??? ????????? ??????")
   public Object update(@Valid @RequestBody PostRequest req, @PathVariable int temp) {
       Post p = postDao.findPostByArticleId(req.getArticleId());

       String token = req.getToken();
       User jwtuser = jwtService.getUser(token);
       int userId;
       Optional<User> userOpt = userDao.findUserByEmail(jwtuser.getEmail());
       if (p != null) {
           userId = userOpt.get().getUserId();
       } else {
           return new ResponseEntity<>("????????? ????????? ???????????????(token??? ???????????? ??????)", HttpStatus.BAD_REQUEST);
       }

       if (userOpt.get().getUserId() != p.getUserId()) {
           return new ResponseEntity<>("???????????? ????????? ???????????? ???????????? ????????????.", HttpStatus.NOT_FOUND);
       }

       String endDate = req.getEndDate();
       String endT = req.getEndTime();
       LocalDateTime endTime = null;
       if (endDate != null || endT != null) {
           endTime = LocalDateTime.parse(endDate + " " + endT, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
       }

       if (temp == 0) {
           Post post = postDao.getPostByArticleId(req.getArticleId());
           post.setCategoryId(req.getCategoryId());
           post.setTitle(req.getTitle());
           post.setAddress(req.getAddress());
           post.setDescription(req.getDescription());
           post.setMinPrice(req.getMinPrice());
           post.setUrlLink(req.getUrlLink());
           post.setImage(req.getImage());
           post.setTemp(temp);
           post.setEndTime(endTime);

           postDao.save(post);

           System.out.println("????????? ??????");

           return new ResponseEntity<>("????????? ?????? ??????", HttpStatus.OK);
       } else if (temp == 1) {

           Post post = postDao.getPostByArticleId(req.getArticleId());
           if (post.getStatus() == 1) { // ???????????? ?????????????????? ?????????
               post.setCategoryId(req.getCategoryId());
               post.setTitle(req.getTitle());
               post.setAddress(req.getAddress());
               post.setMinPrice(req.getMinPrice());
               post.setDescription(req.getDescription());
               post.setUrlLink(req.getUrlLink());
               post.setImage(req.getImage());
               post.setTemp(temp);
               post.setEndTime(endTime);

               postDao.save(post);

               int myPrice = req.getMyPrice();
               if (myPrice < 0) {
                   String message = "0????????? ?????? ????????????.";
                   return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
               }

               Participant part = participantDao.getParticipantByUserIdAndArticleId(userId, req.getArticleId());
               if (part == null) {// ?????????????????? ??????????????????.
                   return new ResponseEntity<>("???????????? ?????? ????????? ?????????.", HttpStatus.BAD_REQUEST);
               }

               int old_price = part.getPrice();// ?????? ???????????? ????????????

               part.setArticleId(req.getArticleId());
               part.setPrice(myPrice);
               participantDao.save(part);// ????????? DB??? ?????? ??????

               // ????????? sum_price??? ?????????
               post = postDao.getPostByArticleId(req.getArticleId());// ?????? ?????????????????? ?????????
               int sumPrice = post.getSumPrice();// sumPrice??? ?????????
               sumPrice = sumPrice + (myPrice - old_price);// ???????????? ????????? ?????????
               post.setSumPrice(sumPrice);
               postDao.save(post);// ?????? DB??? ?????????

               tagDelete(req.getArticleId());
               // ?????? ??????
               String[] tags = req.getTags();// ?????? ??????
               tagAdd(tags, req.getArticleId());

               System.out.println(post.getArticleId() + "?????? ????????? ?????? ?????? ");
               return new ResponseEntity<>("????????? ?????? ?????? ", HttpStatus.OK);
           } else {
               System.out.println("????????? ???????????? ??????????????????.");
               return new ResponseEntity<>("????????? ???????????? ????????? ?????????.", HttpStatus.OK);
           }
       } else if (temp == 2) { // ???????????????

           Post post = postDao.getPostByArticleId(req.getArticleId());

           post.setTitle(req.getTitle());
           post.setDescription(req.getDescription());
           post.setImage(req.getImage());
           postDao.save(post);

           // ?????? ??????
           tagDelete(req.getArticleId());
           // ?????? ??????
           String[] tags = req.getTags();// ?????? ??????
           tagAdd(tags, req.getArticleId());

           System.out.println(post.getArticleId() + "?????? ????????? ?????? ?????? ");
           return new ResponseEntity<>("????????? ?????? ?????? ", HttpStatus.OK);
       } else {
           return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
       }
   }

   @PutMapping("/post/complete/{articleId}")
   @ApiOperation(value = "????????????")
   public Object complete(@Valid @PathVariable int articleId) {
       Post post = postDao.findPostByArticleId(articleId);
       post.setStatus(4); // ??????????????? ??????
       List<Participant> partlist = participantDao.findParticipantByArticleId(articleId);
       // ???????????? ????????? ..
       for (Participant part : partlist) {
           if (part.getStatus() == 1) {
               User user = userDao.getUserByUserId(part.getUserId());
               if (part.getUserId() == post.getUserId()) {
                   user.setUserPoint(user.getUserPoint() + 50);
               } else {
                   user.setUserPoint(user.getUserPoint() + 30);
               }
               int up = user.getUserPoint();
               if (up <= 1000) {
                   user.setGrade(1);
               } else if (up <= 1500) {
                   user.setGrade(2);
               } else if (up <= 2500) {
                   user.setGrade(3);
               } else if (up <= 4000) {
                   user.setGrade(4);
               } else {
                   user.setGrade(5);
               }
               userDao.save(user);
           } else if (part.getStatus() == 0) {
               part.setStatus(2);// ?????????????????? ?????? ???????????? ????????? ??????
               participantDao.save(part);
           }
       }
       postDao.save(post);

       System.out.println("????????????");
       return new ResponseEntity<>("????????? ?????? ?????? ", HttpStatus.OK);
   }

   @DeleteMapping("/post/delete/{articleId}")
   @ApiOperation(value = "????????????")
   public Object delete(@Valid @PathVariable int articleId) {

       Post post = postDao.getPostByArticleId(articleId);
       if (post.getStatus() == 1) {
           // ????????? ??????
           List<Participant> partList = participantDao.getParticipantByArticleId(articleId);
           int pasize = partList.size();
           for (int i = 0; i < pasize; i++) {
               Participant p = partList.get(i);
               participantDao.delete(p);
           }
           // ?????? ??????
           List<Comment> commentList = commentDao.getCommentByArticleId(articleId);
           int csize = commentList.size();
           for (int i = 0; i < csize; i++) {
               Comment c = commentList.get(i);
               commentDao.delete(c);
           }
           /// ????????? ??????
           List<Like> likeList = likeDao.getLikeByArticleId(articleId);
           int lsize = likeList.size();
           for (int i = 0; i < lsize; i++) {
               Like l = likeList.get(i);
               likeDao.delete(l);
           }
           // ?????? ??????
           tagDelete(articleId);
           postDao.delete(post);
           System.out.println("????????????!! ");
           PostResponse result = new PostResponse();

           return new ResponseEntity<>(result, HttpStatus.OK);
       } else {
           return new ResponseEntity<>("????????? ??? ?????? ????????? ??????????????????.", HttpStatus.OK);
       }
   }

   @PostMapping("/file")
   public String fileTest(@RequestPart("file") MultipartFile ff) throws IllegalStateException, IOException {

       System.out.println(ff.toString());
       String forSaveImg = ff.getOriginalFilename().toLowerCase();
       System.out.println(forSaveImg.toString());
       long nowtime = datetimeTosec(LocalDateTime.now());
       System.out.println(System.getProperty("user.dir") + "\\frontend\\src\\assets\\images\\");
       // File file = new File("C:\\Users\\multicampus\\Desktop\\image\\"+ nowtime +
       // forSaveImg);
       File file = new File("/home/ubuntu/shallwe/s03p13b203/frontend/src/assets/images/" + nowtime + forSaveImg);
       if (!file.getParentFile().exists()) {
           file.getParentFile().mkdirs();
       }
       ff.transferTo(file);
       System.out.println(file.getName());

       return file.getName();

   }

   private static String BeforeCreateTime(LocalDateTime createTime) {
       String result = "";
       int before = 0;
       LocalDateTime nowTime = LocalDateTime.now();
       if (createTime.getYear() <= nowTime.getYear() + 1) {
           if (createTime.getMonthValue() == nowTime.getMonthValue()) {
               if (createTime.getDayOfYear() == nowTime.getDayOfYear()) {
                   if (createTime.getHour() == nowTime.getHour()) {
                       if (createTime.getMinute() == nowTime.getMinute()) {
                           result = "??? 1??? ???";
                       } else {
                           before = nowTime.getMinute() - createTime.getMinute();
                           result = "??? " + before + "??? ???";
                       }
                   } else if (createTime.getHour() == nowTime.getHour() + 1
                           && createTime.getMinute() > nowTime.getMinute()) {
                       before = 60 + nowTime.getMinute() - createTime.getMinute();
                       result = "??? " + before + "??? ???";
                   } else {
                       before = nowTime.getHour() - createTime.getHour();
                       result = "??? " + before + "?????? ???";
                   }
               } else if (createTime.getDayOfYear() == nowTime.getDayOfYear() + 1
                       && createTime.getHour() > nowTime.getHour()) {
                   before = 24 + nowTime.getHour() - createTime.getHour();
                   result = "??? " + before + "?????? ???";
               } else {
                   before = nowTime.getDayOfYear() - createTime.getDayOfYear();
                   result = "??? " + before + "??? ???";
               }
           } else if (createTime.getDayOfYear() == nowTime.getDayOfYear() + 1
                   && createTime.getHour() > nowTime.getHour()) {
               before = 24 + nowTime.getHour() - createTime.getHour();
               result = "??? " + before + "??? ???";
           } else {
               before = nowTime.getDayOfYear() - createTime.getDayOfYear();
               result = "??? " + before + "??? ???";
           }
       } else {
           before = nowTime.getYear() - createTime.getYear();

           result = "??? " + before + "??? ???";
       }
       return result;

   }

   private void tagDelete(int articleId) {
       List<Tag> tList = tagDao.getTagByArticleId(articleId);
       int atsize = tList.size();
       for (int i = 0; i < atsize; i++) {
           Tag l = tList.get(i);
           tagDao.delete(l);
       }
   }

   private void tagAdd(String[] tags, int articleId) {
       for (int i = 0; i < tags.length; i++) {
           Tag tag = new Tag();
           tag.setName(tags[i]);
           tag.setArticleId(articleId);
           tagDao.save(tag);
       }
   }

   private long datetimeTosec(LocalDateTime ldt) {
       long result = 0L;
       result += ((((((((ldt.getYear() - 2000) * 365) + ldt.getDayOfYear()) * 24) + ldt.getHour()) * 60)
               + ldt.getMinute()) * 60);
       return result;
   }
}