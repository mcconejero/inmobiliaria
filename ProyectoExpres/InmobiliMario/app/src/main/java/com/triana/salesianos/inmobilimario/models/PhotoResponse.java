package com.triana.salesianos.inmobilimario.models;

public class PhotoResponse {

    private String id;
    private PostResponse postId;
    private String imgurlink;
    private String deletehash;

    public PhotoResponse() {

    }

    public PhotoResponse(PostResponse postId, String imgurlink, String deletehash) {
        this.postId = postId;
        this.imgurlink = imgurlink;
        this.deletehash = deletehash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PostResponse getPostId() {
        return postId;
    }

    public void setPostId(PostResponse postId) {
        this.postId = postId;
    }

    public String getImgurlink() {
        return imgurlink;
    }

    public void setImgurlink(String imgurlink) {
        this.imgurlink = imgurlink;
    }

    public String getDeletehash() {
        return deletehash;
    }

    public void setDeletehash(String deletehash) {
        this.deletehash = deletehash;
    }

}
