package edu.cnm.deepdive.emojipets;

import edu.cnm.deepdive.emojipets.pojo.Player;
import edu.cnm.deepdive.emojipets.pojo.SfAuthenticator;
import edu.cnm.deepdive.emojipets.pojo.SfCredentials;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthenticateService {

  @POST("/")
  @Headers("Content-Type: application/x-www-form-urlencoded")
  Call<SfAuthenticator> getSfToken(@Body SfCredentials sfCredentials);

}
