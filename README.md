
<h1 align="center">🎓 Advanced Student Management System</h1>

<p align="center">
A production-level Java application with SQLite database and REST API integration 🚀
</p>

<hr>

<h2>📌 Project Overview</h2>

<p>
The <b>Advanced Student Management System</b> is a full-featured Java-based application designed to manage student records in a structured, secure, and scalable way.
</p>

<p>
Unlike basic CRUD projects that rely on in-memory storage, this system uses a <b>SQLite relational database</b> to ensure <b>data persistence, integrity, and reliability</b>.
</p>

<p>
It not only performs standard operations like adding, updating, and deleting student records, but also introduces <b>real-world backend capabilities</b> such as:
</p>

<ul>
  <li>Secure authentication system</li>
  <li>Database-driven operations using SQL</li>
  <li>Analytics using aggregate queries</li>
  <li>Embedded REST API for web integration</li>
</ul>

<p>
This project acts as a <b>bridge between a console application and a backend server</b>, making it highly valuable for learning real-world software development concepts.
</p>

<hr>

<h2>🔥 Key Features</h2>

<ul>
  <li>📝 Student Registration with validation (Regex + duplicate check)</li>
  <li>🔐 Login Authentication using database verification</li>
  <li>📋 View All Students (sorted & formatted output)</li>
  <li>🔍 Search Student by ID</li>
  <li>✏️ Dynamic Update (Branch / Password / Both)</li>
  <li>❌ Delete Student Records</li>
  <li>📊 Branch-wise Statistics using SQL GROUP BY</li>
  <li>🌐 Built-in REST API serving JSON data</li>
</ul>

<hr>

<h2>🎯 Use Cases</h2>

<ul>
  <li>🏫 <b>College Management:</b> Store and manage student records efficiently</li>
  <li>👨‍💻 <b>Developer Practice:</b> Learn Java + JDBC + SQL + API integration</li>
  <li>📊 <b>Admin Dashboard Backend:</b> Can act as backend for web/mobile apps</li>
  <li>🧪 <b>Academic Projects:</b> Ideal for mini/major projects in engineering</li>
  <li>🌐 <b>Frontend Integration:</b> Connect with React, Angular, or Vue apps</li>
</ul>

<hr>

<h2>🧠 System Architecture</h2>

<ul>
  <li><b>Person.java</b> → Abstract class (Abstraction & Inheritance)</li>
  <li><b>Operations.java</b> → Interface defining system contract</li>
  <li><b>StudentMain.java</b> → Core logic & execution flow</li>
  <li><b>DatabaseHelper.java</b> → Database connection & initialization</li>
  <li><b>ApiServer.java</b> → Embedded REST API server</li>
</ul>

<p>
This modular structure ensures <b>clean code, scalability, and maintainability</b>.
</p>

<hr>

<h2>🛠️ Tech Stack</h2>

<ul>
  <li><b>Language:</b> Java ☕</li>
  <li><b>Database:</b> SQLite</li>
  <li><b>Connectivity:</b> JDBC</li>
  <li><b>API:</b> Java HttpServer</li>
  <li><b>Concepts:</b> OOP, REST API, SQL, Security</li>
</ul>

<hr>

<h2>🔐 Security Features</h2>

<ul>
  <li>✔️ PreparedStatements (Prevents SQL Injection)</li>
  <li>✔️ Input Validation using Regex</li>
  <li>✔️ Try-With-Resources (No memory leaks)</li>
</ul>

<hr>

<h2>⚙️ Installation & Setup</h2>

<ol>
  <li>Clone the repository</li>
  <li>Open in VS Code / IntelliJ</li>
  <li>Add SQLite JDBC dependency</li>
  <li>Run <b>StudentMain.java</b></li>
</ol>

<hr>

<h2>🚀 How It Works</h2>

<p>
The system runs on a menu-driven interface where users can interact with different options like registration, login, search, update, and delete.
</p>

<p>
All operations are executed using SQL queries on a <b>SQLite database</b>, ensuring real-time data updates and persistence.
</p>

<hr>

<h2>🌐 REST API</h2>

<ul>
  <li>Start API → Select Option 8</li>
  <li>Endpoint → <code>http://localhost:8080/api/students</code></li>
  <li>Response → JSON format</li>
</ul>

<p>
This allows integration with modern frontend frameworks like <b>React, Angular, and Vue</b>.
</p>

<hr>

<h2>📊 Database Schema</h2>

<pre>
students
--------
id (TEXT, PRIMARY KEY)
name (TEXT)
branch (TEXT)
password (TEXT)
</pre>

<hr>

<h2>🚧 Future Improvements</h2>

<ul>
  <li>🔒 Password hashing for enhanced security</li>
  <li>🌍 Web-based UI (React/Angular)</li>
  <li>📈 Advanced analytics dashboard</li>
  <li>🔊 Voice-based interaction</li>
</ul>

<hr>

<h2>👨‍💻 Author</h2>

<p>
<b>Tejaswy</b><br>
CSE Student | Developer | Building Real-World Projects 🚀
</p>

<hr>

<h2>⭐ Final Note</h2>

<p>
This project demonstrates strong knowledge of <b>Java, OOP, SQL, backend development, and API design</b>.
It is designed to simulate a <b>real-world backend system</b>, not just a basic academic project.
</p>

<p align="center">
🔥 If you like this project, give it a star!
</p>
