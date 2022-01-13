package exodia.domain.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass

public abstract class BaseEntity {


    public BaseEntity(){

    }
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    @Column(name = "ID")
    private String id;

    public String getId(){
        return id;

    }
    public void setId(String id){
        this.id = id;
    }
}
