package vijay.bild.model;

public class Category {

    private String Category;
    private String postid;
    private String userid;

    public Category(){

    }

    public Category(String Category,String postid,String userid){
        this.Category = Category;
        this.postid = postid;
        this.userid = userid;
    }
    public String getCategory(){
        return Category;
    }
    public void setCategory(){
        this.Category = Category;
    }
    public String getPostid(){
        return postid;
    }
    public void setPostid(){
        this.postid = postid;
    }
    public String getUserid(){
        return userid;
    }
    public void setUserid(){
        this.userid = userid;
    }

}
