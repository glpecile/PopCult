package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.models.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsernameForm {
    private List<Integer> user;

    public List<Integer> getUser() {
        if (user == null) return new ArrayList<>();
        return user;
    }

    public void setUser(List<Integer> user) {
        this.user = user;
    }

    public Map<Integer, String> generateUserMap(List<User> userList) {
        if (userList == null) return new HashMap<>();
        Map<Integer, String> map = new HashMap<>();
        for (User m : userList) {
            map.put(m.getUserId(), " " + m.getUsername());
        }
        return map;
    }
}
