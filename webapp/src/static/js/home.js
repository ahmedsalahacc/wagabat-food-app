import { initializeApp } from "https://www.gstatic.com/firebasejs/9.15.0/firebase-app.js";
import {
  getAuth,
  onAuthStateChanged,
  signOut,
} from "https://www.gstatic.com/firebasejs/9.15.0/firebase-auth.js";
import {
  getDatabase,
  ref,
  set,
  get,
  child,
} from "https://www.gstatic.com/firebasejs/9.15.0/firebase-database.js";

const firebaseConfig = {
  apiKey: "AIzaSyAtHqhJbAs_Fx0xHt7idF-M324ZG9jMwEs",
  authDomain: "wagabat-94f26.firebaseapp.com",
  databaseURL: "https://wagabat-94f26-default-rtdb.firebaseio.com",
  projectId: "wagabat-94f26",
  storageBucket: "wagabat-94f26.appspot.com",
  messagingSenderId: "391201027074",
  appId: "1:391201027074:web:ec0abc2375aad7f849631b",
  measurementId: "G-TME3STFJWG",
};

let restId = null;

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const auth = getAuth(app);
const database = getDatabase(app);

onAuthStateChanged(auth, async (user) => {
  if (user != null) {
    console.log("user", user);
    restId = user.uid;
    updateResturantWelcomeState();
  } else {
    alert("login required");
    console.log(user);
    window.location.href = "http://127.0.0.1:8080/html/login.html";
  }
});

document.getElementById("btn_logout").addEventListener("click", (e) => {
  signOut(auth),
    then(() => {
      window.replace("http://127.0.0.1:8080/html/login.html");
    }).catch((error) => {
      alert(error);
    });
});

document.getElementById("submit").addEventListener("click", (e) => {
  e.preventDefault();
  const name = document.getElementById("input_dishname").value;
  const desc = document.getElementById("input_dishdesc").value;
  const price = document.getElementById("input_price").value;
  const img = document.getElementById("input_dishimg").value;
  addDishToResturant(restId, name, desc, price, img);
});

function uuidv4() {
  return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, (c) =>
    (
      c ^
      (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))
    ).toString(16)
  );
}

function updateOrdersState() {}

function updatePreviousOrdersState() {}

async function addDishToResturant(rid, dishName, dishDesc, dishPrice, dishImg) {
  // creating nodes for user in realtime db
  const id = uuidv4();
  const pathRest = "resturants/" + rid + "/dishes/" + id;
  const pathDish = "dishes/" + id;
  // creating nodes for user in realtime db
  await set(ref(database, pathDish), {
    name: dishName,
    description: dishDesc,
    img: dishImg,
    price: dishPrice,
  }).then(async () => {
    // creating nodes for user in realtime db
    await set(ref(database, pathRest), {
      id: id,
    });
  });
}

function updateResturantWelcomeState() {
  const path = "resturants/" + restId + "/name";
  get(child(ref(database), path))
    .then((snapshot) => {
      if (snapshot.exists()) {
        document.getElementById("h1_title").textContent =
          "Welcome, " + snapshot.val();
      } else {
        console.log(path, "is not available");
      }
    })
    .catch((error) => {
      console.log(error);
    });
}
