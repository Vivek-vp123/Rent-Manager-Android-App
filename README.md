# 🏠 Rent Manager App

Rent Manager is a simple and efficient Android app designed to help users manage their monthly rent payments. It allows users to keep track of rent payments along with electricity bills in a user-friendly way.

---

## 📱 App Features

- **Add Rent Entries**: Users can add a new rent entry with the date, rent amount, and electricity bill.
- **View Rent History**: A list of all previous rent entries is displayed for easy access.
- **Delete Entries**: Users can remove any entry with a simple tap on the delete icon.
- **Scroll Support**: Entries are scrollable when the list exceeds screen height.
- **SQLite with Room**: Data is stored locally using the Room persistence library for fast and reliable access.

---

## 🚀 Screenshots

<p align="center"> 
    <img src="assets/screenshot1.png" alt="Rent Manager App Screenshot" width="200"> 
    <img src="assets/screenshot2.png" alt="Rent Manager App Screenshot" width="200">
</p>

---

## 🛠️ Tech Stack

- **Android SDK**
- **Java**
- **Room Persistence Library**
- **SQLite**
- **XML Layouts**
- **Material Design Components**

---

## ⚙️ Installation and Setup

To get a local copy of this project up and running, follow these steps:


### Clone the Repository

Open your terminal and run:

```bash
git clone https://github.com/your-username/rent-manager.git
cd rent-manager
```
## Open in Android Studio
1. Open **Android Studio** and select the cloned project directory to import it into the IDE.

## Build the Project
1. After importing, click on `Build > Rebuild Project` to build the project dependencies.

## Run on Emulator or Physical Device
1. Connect a physical device or use an emulator.
2. Hit the **Run** button in Android Studio to launch the app.

---

## 💾 Database (Room Persistence Library)
The app uses the **Room Persistence Library** to store rent entries. All entries are saved locally in a **SQLite** database, so your data remains available even if the app is closed.

---

## 📄 Key Code Components

- **`roomActivity.java`**  
  This class handles the main business logic for adding, viewing, and deleting rent entries. It uses an `AsyncTask` for database operations to avoid blocking the main UI thread.

- **`Entry.java`**  
  Data class representing the rent entry with fields for date, rent amount, and electricity bill.

- **`AppDatabase.java`**  
  Singleton class for initializing the Room Database.

---

## 🌟 How to Contribute
Contributions are welcome! To contribute:

1. Fork this repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. Push to the branch (`git push origin feature-branch`).
5. Open a pull request.

---


## 🤝 Acknowledgments
Thanks to the **Android Developer community** for the Room Library and other open-source tools that made this project possible.

