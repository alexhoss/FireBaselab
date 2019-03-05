package bcit.lab.firebaselab;

import java.util.Date;

public class Todo {


    String task;
    String who;
    Date date;
    boolean done;

    public Todo(){}

    public Todo(String field, String whomst, Date date){
        task = field;
        who = whomst;
        this.date = date;
        done = false;
    }



    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }



}
