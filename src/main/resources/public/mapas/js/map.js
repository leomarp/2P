
Set up some of our variables.
var map; //Will contain map object.
var marker = false; ////Has the user plotted their location marker?

//Function called to initialize / create the map.
//This is called when the page has loaded.
function initMap(forms) {

    console.log(forms)
    console.log(forms[0].latitud);
    console.log(forms[0].longitud);
    console.log(forms[0].formulario.sector);

    //The center location of our map.
    var centerOfMap = new google.maps.LatLng(19.220847542987812, -70.52509807102038);

    //Map options.
    var options = {
      center: centerOfMap, //Set center.
      zoom: 8 //The zoom value.
    };

    //Create the map object.
    map = new google.maps.Map(document.getElementById('map'), options);

    //Listen for any clicks on the map.
//    google.maps.event.addListener(map, 'click', function(event) {
//        //Get the location that the user clicked.
//        var clickedLocation = event.latLng;
//        //If the marker hasn't been added.
//        if(marker === false){
//            //Create the marker.
//            marker = new google.maps.Marker({
//                position: clickedLocation,
//                map: map,
//                draggable: true //make it draggable
//            });
//            //Listen for drag events!
//            google.maps.event.addListener(marker, 'dragend', function(event){
//                markerLocation();
//            });
//        } else{
//            //Marker has already been added, so just change its location.
//            marker.setPosition(clickedLocation);
//        }
//        //Get the marker's location.
//
//        markerLocation();
//    });
    multipleDirections(map);
}

//This function will get the marker's current location and then add the lat/long
//values to our textfields so that we can save the location.
function markerLocation(){
    //Get location.
    var currentLocation = marker.getPosition();
    //Add lat and lng values to a field that we can save.
    document.getElementById('lat').value = currentLocation.lat(); //latitude
    document.getElementById('lng').value = currentLocation.lng(); //longitude
}

function showDirections(){
    var myLatlng = new google.maps.LatLng(-25.363882,131.044922);
    var mapOptions = {
      zoom: 4,
      center: myLatlng
    }
    var map = new google.maps.Map(document.getElementById("map"), mapOptions);

    var marker = new google.maps.Marker({
        position: myLatlng,
        title:"Hello World!"
    });

    marker.setMap(map);

}

function multipleDirections(map){
     var locations = [
          ['Bondi Beach', 19.39582743702772, -71.22364957899867],
          ['Coogee Beach', 19.46317180914556, -70.19093473524867],
          ['Cronulla Beach', 19.747779942899495, -71.41591032118617],
          ['Manly Beach', 19.540842103659852, -70.78419645399867],
          ['Maroubra Beach', 19.3440050979676, -71.07533414931117]
        ];


            var infowindow = new google.maps.InfoWindow();

            var marker, i;

        for (i = 0; i < locations.length; i++) {
              marker = new google.maps.Marker({
                position: new google.maps.LatLng(locations[i][1], locations[i][2]),
                map: map
              });

              google.maps.event.addListener(marker, 'click', (function(marker, i) {
                return function() {
                  infowindow.setContent(locations[i][0]);
                  infowindow.open(map, marker);
                }
              })(marker, i));
            }


}
function myFunction() {
  document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown menu if the user clicks outside of it
window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}

document.getElementById("myBtn").addEventListener("click", multipleDirections);

////Load the map when the page has finished loading.
//google.maps.event.addDomListener(window, 'load', initMap);