// Import the functions you need from the SDKs you need
import { initializeApp } from "https://www.gstatic.com/firebasejs/9.15.0/firebase-app.js";
import {
  getDatabase,
  ref,
  set,
} from "https://www.gstatic.com/firebasejs/9.15.0/firebase-database.js";
import {
  getAuth,
  onAuthStateChanged,
  createUserWithEmailAndPassword,
} from "https://www.gstatic.com/firebasejs/9.15.0/firebase-auth.js";
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
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

export const usersHash = {};

// trick to wait for registration function to finish before
let promiseHolder = async function () {};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const auth = getAuth(app);
const database = getDatabase(app);

onAuthStateChanged(auth, async (user) => {
  if (user != null && promiseHolder) {
    await promiseHolder;
    window.location.href = "http://127.0.0.1:8080/html/home.html";
  } else {
    console.log("No user");
  }
});

document.getElementById("btn_submit").addEventListener("click", async (e) => {
  e.preventDefault();
  const email = document.getElementById("input_email").value;
  const password = document.getElementById("input_password").value;
  const delPrice = document.getElementById("input_delivery_price").value;
  const delTime = document.getElementById("input_delivery_time").value;
  const category = document.getElementById("input_category").value;
  const img = document.getElementById("input_rest_img").value;
  const name = document.getElementById("input_name").value;

  console.log("email", email);
  console.log("password", password);
  promiseHolder = createNewResturantUser(
    name,
    email,
    password,
    delPrice,
    delTime,
    category,
    img
  );
});

/**
 * creates a new resturant user with the requested parameters
 * handling the required authentication and database interfacing
 * procedures
 * @param {String} name
 * @param {String} email
 * @param {String} password
 * @param {number} deliveryPrice
 * @param {number} deliveryTime
 * @param {String} category
 * @param {String} img
 */
async function createNewResturantUser(
  name,
  email,
  password,
  deliveryPrice,
  deliveryTime,
  category,
  img
) {
  await createUserWithEmailAndPassword(auth, email, password)
    .then(async (userCredential) => {
      // Signed in
      console.log("here1");
      const user = userCredential.user;
      const uid = user.uid;
      usersHash[email] = uid;

      // creating nodes for user in realtime db
      await set(ref(database, "resturants/" + uid), {
        name: name,
        email: email,
        img: img,
        rating: Math.floor(Math.random() * (5 - 3 + 1) + 3).toFixed(1),
        "delivery-price": deliveryPrice,
        "delivery-time": deliveryTime,
        category: category,
      });
    })
    .catch((error) => {
      const errorCode = error.code;
      const errorMessage = error.message;
      console.log("error:" + errorMessage + ":" + errorCode);
    });
}
