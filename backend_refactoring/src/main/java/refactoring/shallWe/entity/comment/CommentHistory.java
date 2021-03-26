package refactoring.shallWe.entity.comment;


import javax.persistence.*;

//TODO 댓글 수정시 수정 전 내용 저장소
@Entity
@Table(name ="CommentHistory")
public class CommentHistory extends Comment {
    @Id @GeneratedValue
    @Column(name = "comment_history_id")
    private Long id;

    public CommentHistory(){

    }
    public CommentHistory(Comment comment){
//        this =
    }
}
