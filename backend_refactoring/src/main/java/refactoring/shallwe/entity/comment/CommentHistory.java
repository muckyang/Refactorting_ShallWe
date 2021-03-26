package refactoring.shallwe.entity.comment;


import javax.persistence.*;

//TODO 댓글 수정시 수정 전 내용 저장소
@Entity
@Table(name ="Comment_history")
public class CommentHistory {
    @Id @GeneratedValue
    @Column(name = "comment_history_id")
    private Long id;

//    public CommentHistory(){
//
//    }
//    public CommentHistory(Comment comment){
////        this =
//    }
}
