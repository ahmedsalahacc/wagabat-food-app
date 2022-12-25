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

// initialize UI components
const emailContainer = document.getElementById("input_email");
const passwordContainer = document.getElementById("input_password");
const delPriceContainer = document.getElementById("input_delivery_price");
const delTimeContainer = document.getElementById("input_delivery_time");
const categoryContainer = document.getElementById("input_category");
const imgContainer = document.getElementById("input_rest_img");
const nameContainer = document.getElementById("input_name");

// event listeners
document.getElementById("btn_submit").addEventListener("click", async (e) => {
  e.preventDefault();
  const email = emailContainer.value;
  const password = passwordContainer.value;
  const delPrice = delPriceContainer.value;
  const delTime = delTimeContainer.value;
  const category = categoryContainer.value;
  const img = imgContainer.value;
  const name = nameContainer.value;

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
        rating: (Math.random() * (4 - 3 + 1) + 3).toFixed(1),
        "delivery-price": deliveryPrice,
        "delivery-time": deliveryTime,
        category: category,
      });
    })
    .then(() => {
      nameContainer.value = "";
      imgContainer.value = "";
      delPriceContainer.value = "";
      passwordContainer.value = "";
    })
    .catch((error) => {
      const errorCode = error.code;
      const errorMessage = error.message;
      console.log("error:" + errorMessage + ":" + errorCode);
    });
}
