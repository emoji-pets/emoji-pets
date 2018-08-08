package edu.cnm.deepdive.emojipets;

import edu.cnm.deepdive.emojipets.pojo.Player;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * This is the EmojiPetService interface.
 * It holds GET, POST, and PUT methods.
 * The Get method is used to retrieve information.
 * The Post method is used to send a resource request at the URI to do something with the provided entity.
 * The Put methos is used to store an entity at a URI.
 */
public interface EmojiPetService {

  @GET("players/{oauthId}")
  Call<Player> get(@Header("Authorization") String authorization, @Path("oauthId") String oauthId);

  @POST("players")
  Call<Player> addUser(@Header("Authorization") String authorization,
      @Body Player player);

  @PUT("players/{oauthId}")
  Call<Player> updatePlayer(@Header("Authorization") String authorization, @Path("oauthId") String oauthId,
      @Body Player player);

  @GET("players/")
  Call<List<Player>> getAllPlayers(@Header("Authorization") String authorization);

  @POST("players/{player_oauthId}/follow/{other_player_oauthId}")
  Call<Player>postFollow(@Header("Authorization") String authorization,
      @Path("player_oauthId") String playerOauthId, @Path("other_player_oauthId") String otherPlayerOauthId,
      @Body Player player);

}
