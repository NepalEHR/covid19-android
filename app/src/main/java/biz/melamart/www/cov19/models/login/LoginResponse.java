package biz.melamart.www.cov19.models.login;
public class LoginResponse {
        String session_token;
        String session_id;
        int id;
        String name;
        String first_name;
        String last_name;
        String email;
        boolean is_sys_admin;
        String last_login_date;
        String host;

public String getSession_token() {
        return session_token;
        }

public String getSession_id() {
        return session_id;
        }

public int getId() {
        return id;
        }

public String getName() {
        return name;
        }

public String getFirst_name() {
        return first_name;
        }

public String getLast_name() {
        return last_name;
        }

public String getEmail() {
        return email;
        }

public boolean isIs_sys_admin() {
        return is_sys_admin;
        }

public String getLast_login_date() {
        return last_login_date;
        }

public String getHost() {
        return host;
        }
        }