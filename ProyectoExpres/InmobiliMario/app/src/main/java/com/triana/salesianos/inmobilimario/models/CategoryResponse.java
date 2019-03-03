package com.triana.salesianos.inmobilimario.models;

<<<<<<< HEAD
import java.io.Serializable;

public class CategoryResponse implements Serializable {

    private String id;
    private String name;

    public CategoryResponse() {}

    public CategoryResponse(String name) {
=======
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    public CategoryResponse(String id, String name) {
        this.id = id;
>>>>>>> b0d635f599e5af8c945e3afd1296b091c4fc0301
        this.name = name;
    }

    public String getId() {
        return id;
    }

<<<<<<< HEAD
    public void setId(String id) {
        this.id = id;
=======
    public CategoryResponse setId(String id) {
        this.id = id;
        return this;
>>>>>>> b0d635f599e5af8c945e3afd1296b091c4fc0301
    }

    public String getName() {
        return name;
    }

<<<<<<< HEAD
    public void setName(String name) {
        this.name = name;
    }

=======
    public CategoryResponse setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "CategoryResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
>>>>>>> b0d635f599e5af8c945e3afd1296b091c4fc0301
}
