function onSignIn(googleUser) {
  var profile = googleUser.getBasicProfile();
  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
  console.log('Name: ' + profile.getName());
  console.log('Image URL: ' + profile.getImageUrl());
  console.log('Email: ' + profile.getEmail());

  $.post("/validate_id_token",
         {"id_token": googleUser.getAuthResponse().id_token}
  ).done(function (res) {
    console.log("response:", res);
    if (res.isSuccess) {
      window.location.replace(res.redirectPath);
    } else {
      alert("Couldn't validate id token", res.errorMessage);
    }
  });
}

function onSignInFailure(error) {
  console.log(error);
  alert(error);
}
