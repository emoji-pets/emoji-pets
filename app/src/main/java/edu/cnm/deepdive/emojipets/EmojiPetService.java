package edu.cnm.deepdive.emojipets;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface EmojiPetService {

  @GET("emojipet")
  Call<String[]> get(@Header("Authorization") String authorization);

}
