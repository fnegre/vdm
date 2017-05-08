package fr.fnegre.interfaces.rest.reading;

import java.util.List;

public class PostsView {
    private List<VdmView> posts;
    private int count;

    public List<VdmView> getPosts() {
        return posts;
    }

    public void setPosts(List<VdmView> posts) {
        this.posts = posts;
    }

    public int getCount() {
        return getPosts().size();
    }

}
