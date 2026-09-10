# Jetpack Compose Calculator App

A modern, responsive, and robust Android calculator application built with Kotlin, Jetpack Compose, and Material Design 3. Features clean architecture (MVVM), live expression parsing using `exp4j`, and edge-case handling.

## 📱 Features

- **Live Expression Evaluation:** Instant result previews as you type.
- **Complex Math Parsing:** Full support for order of operations (PEMDAS), parentheses, percentages, and decimals.
- **Edge-Case Handling:** Prevents invalid operator sequences, invalid decimals, and handles division by zero safely (`Cannot divide by zero`).
- **Clean Formatting:** Clean output formatting preventing raw scientific notation for regular decimal values.
- **Material 3 UI:** Dark-themed responsive grid design built natively with Jetpack Compose.

## 🛠️ Tech Stack & Architecture

- **Language:** [Kotlin](https://kotlinlang.org/)
- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Declarative UI)
- **Design System:** Material Design 3
- **Architecture Pattern:** MVVM (Model-View-ViewModel)
- **State Management:** Compose State (`mutableStateOf`) & ViewModel
- **Math Parsing Engine:** [`exp4j`](https://www.objecthunter.net/exp4j/) (v0.4.8)

## 📁 Project Structure

```text
com.example.codenimbuscalculator/
├── MainActivity.kt           # Entry point and Jetpack Compose UI Grid layout
└── CalculatorViewModel.kt    # State management, user actions, & exp4j evaluation engine

🚀 Getting Started
Prerequisites
Android Studio: Ladybug (2024.2.1) or newer recommended

JDK: 17 or higher

Minimum SDK: API 24 (Android 7.0)

How to Run
Clone the repository:

Bash
git clone [https://github.com/KRISHNA-JEE/kotlin-calculator-app.git](https://github.com/KRISHNA-JEE/kotlin-calculator-app.git)
Open in Android Studio: Launch Android Studio, select Open, and navigate to the cloned project folder.

Gradle Sync: Allow Android Studio to automatically download exp4j and Jetpack Compose dependencies during project sync.

Run App: Select an Android Virtual Device (Emulator) or a physical device connected via USB debugging and click Run (▶) (Shift + F10).