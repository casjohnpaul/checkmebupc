package com.bupc.checkme.core.database.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.bupc.checkme.core.model.Item;

/**
 * Created by casjohnpaul on 8/13/2017.
 */

public class Question implements Item, Parcelable {

    public static final String TABLE_NAME = "quiz";

    public static final String ID = "id";
    public static final String WORD = "word";
    public static final String DESCRIPTION = "description";
    public static final String GROUP = "group";
    public static final String LEVEL = "level";
    public static final String ANSWER = "answer";

    private int id;
    private String lvl; // easy, medium, hard
    private String word;
    private String description;
    private String group; // a, b, c, d, e, f...
    private boolean answer;

    private Question(Builder builder) {
        setId(builder.id);
        setLvl(builder.lvl);
        setWord(builder.word);
        setDescription(builder.description);
        setGroup(builder.group);
        setAnswerd(builder.isAnswerd);
    }

    protected Question(Parcel in) {
        id = in.readInt();
        lvl = in.readString();
        word = in.readString();
        description = in.readString();
        group = in.readString();
        answer = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(lvl);
        dest.writeString(word);
        dest.writeString(description);
        dest.writeString(group);
        dest.writeByte((byte) (answer ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLvl() {
        return lvl;
    }

    public void setLvl(String lvl) {
        this.lvl = lvl;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isAnswerd() {
        return answer;
    }

    public void setAnswerd(boolean answerd) {
        answer = answerd;
    }

    public int getAnswer() {
        return answer ? 1 : 0;
    }


    public static final class Builder {
        private int id;
        private String lvl;
        private String word;
        private String description;
        private String group;
        private boolean isAnswerd;

        public Builder() {
        }

        public Builder id(int val) {
            id = val;
            return this;
        }

        public Builder lvl(String val) {
            lvl = val;
            return this;
        }

        public Builder word(String val) {
            word = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder group(String val) {
            group = val;
            return this;
        }

        public Builder isAnswerd(boolean val) {
            isAnswerd = val;
            return this;
        }

        public Question build() {
            return new Question(this);
        }
    }
}
