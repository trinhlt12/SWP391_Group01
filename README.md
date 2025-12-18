# SWP391 - ONLINE PHONE CARD STORE (Group 1)

University Project: Building an online phone card selling system.
**Tech Stack:** Java Servlet, JSP, JSTL, MySQL, Apache Tomcat 9, Maven.

---

## PREREQUISITES

All team members must install the exact versions listed below to avoid environment conflicts:
*   **IDE:** IntelliJ IDEA Ultimate (Recommended) or Eclipse Enterprise.
*   **JDK:** Version **17** (LTS).
*   **Server:** Apache Tomcat **9.0.x**.
*   **Database:** MySQL 8.0.
*   **Build Tool:** Maven.

---

## SETUP GUIDE

For new members cloning the project for the first time:

1.  **Clone Project:**
    ```bash
    git clone <your-repo-link-here>
    ```

2.  **Open in IntelliJ:**
    *   Select **Open** -> Choose the `pom.xml` file -> **Open as Project**.
    *   Wait for Maven to download dependencies (Load Maven Changes).

3.  **Database Configuration:**
    *   Navigate to `src/main/resources`.
    *   Copy `db.properties.example` and rename it to `db.properties`.
    *   Update `db.user` and `db.password` to match your local MySQL credentials.

4.  **Initialize Database:**
    *   Open MySQL Workbench (or IntelliJ Database Tool).
    *   Run the `database.sql` to create tables and dummy data.

5.  **Tomcat Configuration:**
    *   Click **Add Configuration** (top right) -> **+** -> **Tomcat Server** -> **Local**.
    *   **Deployment** tab: Add Artifact `...:war exploded`.
    *   **Server** tab: Change **Application context** to `/SWP391_Group01`.

6.  **Run**
