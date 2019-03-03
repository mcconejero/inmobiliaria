package com.triana.salesianos.inmobilimario.models;

public class PhotoUploadResponse {

    private String id;
    private String postId;
    private String imgurlink;
    private String deletehash;

    public PhotoUploadResponse(String id, String postId, String imgurlink, String deletehash) {
        this.id = id;
        this.postId = postId;
        this.imgurlink = imgurlink;
        this.deletehash = deletehash;
    }

    public PhotoUploadResponse() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
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
