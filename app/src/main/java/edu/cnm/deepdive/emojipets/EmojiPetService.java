package edu.cnm.deepdive.emojipets;

import edu.cnm.deepdive.emojipets.pojo.Player;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EmojiPetService {

  @GET("players/{oauthId}")
  Call<Player> get(@Header("Authorization") String authorization, @Path("oauthId") String oauthId);

  @POST("players")
  Call<Player> addUser(@Header("Authorization") String authorization,
      @Body Player player);



}
