# 📱 SWP391 - ONLINE PHONE CARD STORE (Group 1)

University Project: Building an online phone card selling system.
**Tech Stack:** Java Servlet, JSP, JSTL, MySQL, Apache Tomcat 9, Maven.

---

## 🛠️ PREREQUISITES

All team members must install the exact versions listed below to avoid environment conflicts:
*   **IDE:** IntelliJ IDEA Ultimate (Recommended) or Eclipse Enterprise.
*   **JDK:** Version **17** (LTS).
*   **Server:** Apache Tomcat **9.0.x** (Do NOT use Tomcat 10+ due to `javax` vs `jakarta` library changes).
*   **Database:** MySQL 8.0.
*   **Build Tool:** Maven.

---

## 🚀 SETUP GUIDE

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
    *   Copy `db.properties.example` (if exists) and rename it to `db.properties`.
    *   Update `db.user` and `db.password` to match your local MySQL credentials.

4.  **Initialize Database:**
    *   Open MySQL Workbench (or IntelliJ Database Tool).
    *   Run the `database.sql` script (located in root or resources) to create tables and dummy data.

5.  **Tomcat Configuration:**
    *   Click **Add Configuration** (top right) -> **+** -> **Tomcat Server** -> **Local**.
    *   **Deployment** tab: Add Artifact `...:war exploded`.
    *   **Server** tab: Change **Application context** to `/` (for clean URLs like localhost:8080/).

6.  **Run:** Click the green Play button ▶️.

---

## 📜 GIT WORKFLOW (MANDATORY)

To prevent conflicts and code loss, the entire team must strictly follow the **Feature Branch Workflow**.

### ⛔ THE 3 GOLDEN RULES
1.  **NEVER** push directly to the `main` branch.
2.  **NEVER** commit system files (`.idea`, `target`, `out`, `*.iml`) or local config files (`db.properties`).
3.  **ALWAYS** `git pull origin main` before starting new work.

### 🌿 Branching Strategy
*   **`main`**: The stable branch. Code here must always be runnable.
*   **`feature/[member-name]/[feature-name]`**: Personal working branch.
    *   *Example:* `feature/nam/login-page`, `feature/lan/database-setup`.

### 🔄 Standard Workflow (5 Steps)

**Step 1: Update & Create Branch**
```bash
git checkout main
git pull origin main       # Get latest code
git checkout -b feature/your-name/feature-name
```
**Step 2: Update & Create Branch**
#### Commit messages must be clear and include the module prefix.
✅ Good: [Login] Add authentication logic, [Product] Create product DAO
#### ❌ Bad: "fix bug", "update", "done"

**Step 3: Update & Create Branch**
#### Before pushing, check if main has new updates and merge them into your branch.
```bash
git checkout main
git pull origin main             # Update local main
git checkout feature/your-name/feature-name
git merge main                   # Merge main into your feature branch
# If conflicts occur -> Fix red files -> Commit again
```
**Step 4: Push to Server**
```bash
git push origin feature/your-name/feature-name
```
**Step 5: Create Pull Request (PR)**

1.  **Go to GitHub/GitLab and create a Pull Request to the main branch.**

2.  **Ask a team member to review and merge.**
3.  **Once merged -> Delete the old feature branch.**
