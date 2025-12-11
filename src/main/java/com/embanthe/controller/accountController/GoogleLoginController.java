package com.embanthe.controller.accountController;
import com.embanthe.dao.UserDAO;
import com.embanthe.model.User;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import com.embanthe.constant.Iconstant;

import java.io.IOException;
import java.sql.SQLException;

public class GoogleLoginController {
    public static String getToken(String code) throws ClientProtocolException, IOException {
    String response = Request.Post(Iconstant.GOOGLE_LINK_GET_TOKEN)
            .bodyForm(
                    Form.form()
                            .add("client_id", Iconstant.GOOGLE_CLIENT_ID)
                            .add("client_secret", Iconstant.GOOGLE_CLIENT_SECRET)
                            .add("redirect_uri", Iconstant.GOOGLE_REDIRECT_URI)
                            .add("code", code)
                            .add("grant_type", Iconstant.GOOGLE_GRANT_TYPE)
                            .build()
            )
            .execute().returnContent().asString();

    JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
    String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
    return accessToken;
}

    public static User getUserInfo(final String accessToken) throws ClientProtocolException, IOException, SQLException {
        String link = Iconstant.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link)
                .addHeader("Authorization", "Bearer " + accessToken)
                .execute().returnContent().asString();

        JsonObject json = new Gson().fromJson(response, JsonObject.class);

        String email = json.get("email").getAsString();
        String name = json.has("name") ? json.get("name").getAsString() : "Google User";
        String googleId = json.get("id").getAsString();
        boolean verified = json.get("verified_email").getAsBoolean();

        // Bước cuối: tìm hoặc tạo User trong DB
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByEmail(email);

        if (user == null) {
            // Tạo mới user từ Google
            user = User.builder()
                    .email(email)
                    .fullName(name)
                    .username(email.split("@")[0]) // tránh trùng username
                    .passwordHash("" ) // đánh dấu login bằng Google
                    .role("CUSTOMER")
                    .balance(0.0)
                    .status("ACTIVE")
                    .phone("")
                    .build();

            userDAO.insertGoogleUser(user);
        }

        return user; // trả về User để lưu session
    }

}
