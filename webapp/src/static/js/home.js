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
  onValue,
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

/**
 *
 * @param {*} status
 * @returns
 */
const convertStatusToString = (status) => {
  switch (Number.parseInt(status)) {
    case 2:
      return "Preparing";
    case 3:
      return "Delivered";
    default:
      return "Waiting for confirmation";
  }
};

onAuthStateChanged(auth, async (user) => {
  if (user != null) {
    console.log("user", user);
    restId = user.uid;
    updateResturantWelcomeState();
    updateOrdersForRestaurant();
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

// UI container
const nameContainer = document.getElementById("input_dishname");
const descContainer = document.getElementById("input_dishdesc");
const priceContainer = document.getElementById("input_price");
const imgContainer = document.getElementById("input_dishimg");

document.getElementById("submit").addEventListener("click", (e) => {
  e.preventDefault();
  const name = nameContainer.value;
  const desc = descContainer.value;
  const price = priceContainer.value;
  const img = imgContainer.value;
  addDishToResturant(restId, name, desc, price, img);
});

/**
 * generates UUIDV4 string
 */
function uuidv4() {
  return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, (c) =>
    (
      c ^
      (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))
    ).toString(16)
  );
}

/**
 *
 */
function updatePreviousOrdersState() {}

/**
 *
 */
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
  })
    .then(async () => {
      // creating nodes for user in realtime db
      await set(ref(database, pathRest), {
        id: id,
      });
    })
    .then(() => {
      nameContainer.value = "";
      priceContainer.value = "";
      descContainer.value = "";
      imgContainer.value = "";
    });
}

/**
 *
 */
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

let activeOrders = [];
let allOrders = [];

function updateOrdersForRestaurant() {
  async function getOrderData(orderId) {
    let returnVal;
    const orderPath = "orders/" + orderId;
    await get(child(ref(database), orderPath)).then((snapshot) => {
      if (snapshot.exists()) {
        returnVal = snapshot.val();
      }
    });
    return returnVal;
  }

  let firstTime = true;
  const restaurantRef = "resturants/" + restId;
  get(child(ref(database), restaurantRef))
    .then((snapshot) => {
      // do
      if (snapshot.exists()) {
        let orderIds = snapshot.val()["orders"];

        for (let orderId of Object.keys(orderIds)) {
          getOrderData(orderId).then((data) => {
            if (Number.parseInt(data["status"]) === 1) {
              activeOrders.push({ id: orderId, data: data });
            } else {
              allOrders.push({ id: orderId, data: data });
            }
            console.log("allorders", allOrders);
            console.log("active orders", activeOrders);
            updateActiveOrdersState();
            updateAllOrdersState();
          });
        }
      }
    })
    .catch((error) => {
      console.log(restaurantRef, "is not available");
      console.log("ERR:" + error);
    });
}

/**  */
function updateActiveOrdersState() {
  let activeOrdersHTMLContainerNode =
    document.getElementById("active__container");
  let innerHTMLContainer = "";
  let collapseTracker = 1;
  for (const each of activeOrders) {
    // @TODO get dish items then do the rest
    innerHTMLContainer += `
    <div class="accordion-item">
        <h2 class="accordion-header" id="headingOne">
            <button class="accordion-button" type="button" data-bs-toggle="collapse"
                data-bs-target="#collapse${collapseTracker}" aria-expanded="true" aria-controls="collapse${collapseTracker}">
                OrderID: ${each["id"]}
            </button>
        </h2>
        <div id="collapse${collapseTracker}" class="accordion-collapse collapse" aria-labelledby="headingOne"
            data-bs-parent="#accordionExample">
            <div class="accordion-body">
                <p>
                  <strong>Address:</strong> ${each["data"]["address"]}
                </p>
                <p>
                  <strong>Datetime:</strong> ${each["data"]["datetime"]}
                </p>
                <p>
                  <strong>Price:</strong> ${each["data"]["price"]}
                </p>
                <p>
                  <strong>Status:</strong> ${convertStatusToString(
                    each["data"]["status"]
                  )}
                </p>
                <button class="btn btn-danger btn-order-confirm" id="${
                  each["id"]
                }">Confirm</button>
            </div>
        </div>
    </div>
    `;
    collapseTracker++;
  }
  activeOrdersHTMLContainerNode.innerHTML = innerHTMLContainer;

  // add event listeners to the buttons
  const btns = document.getElementsByClassName("btn-order-confirm");
  for (let each of btns) {
    each.addEventListener("click", () => {
      console.log(each.id);
      changeOrderStatusToAccepted(each.id);
    });
  }
}
function updateAllOrdersState() {
  let ordersHistoryHTMLContainerNode =
    document.getElementById("orders__history");
  let innerHTMLContainer = "";
  for (const each of allOrders) {
    // @TODO get dish items then do the rest
    innerHTMLContainer += `
    <div>
        <div><strong>OrderID:</strong> ${each["id"]}</div>
        <div><strong>Status:</strong> ${convertStatusToString(
          each["data"]["status"]
        )}</div>
        <div><strong>Price:</strong> ${each["data"]["price"]}</div>
        <div><strong>Datetime:</strong> ${each["data"]["datetime"]}</div>
        <div><strong>Address:</strong> ${each["data"]["address"]}</div>
    </div>
    `;
  }
  ordersHistoryHTMLContainerNode.innerHTML = innerHTMLContainer;
}

async function changeOrderStatusToAccepted(id) {
  const orderPath = "orders/" + id;

  get(child(ref(database), orderPath))
    .then(async (snapshot) => {
      // do
      if (snapshot.exists()) {
        let order = snapshot.val();
        await set(ref(database, orderPath), {
          ...order,
          status: 2,
        }).catch((error) => {
          const errorCode = error.code;
          const errorMessage = error.message;
          console.log("error:" + errorMessage + ":" + errorCode);
        });
      }
    })
    .then(() => notifyChanged())
    .catch((error) => {
      console.log(orderPath, "is not available");
      console.log("ERR:" + error);
    });
}

function notifyChanged() {
  activeOrders = [];
  allOrders = [];
  updateOrdersForRestaurant();
}
