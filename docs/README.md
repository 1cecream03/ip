# Gojo - Task Manager Chatbot

> "Throughout heaven and earth, I alone am the honored one." – Satoru Gojo

Gojo is a desktop chatbot that helps you manage your tasks efficiently via a simple command-line interface. Whether it's a quick to-do, a deadline to meet, or an event to attend — Gojo has you covered.

---

## Quick Start

1. Ensure you have **Java 11 or above** installed.
2. Download the latest `gojo.jar` from the releases page.
3. Run the app with: `java -jar gojo.jar`
4. Type a command and press **Enter** to interact with Gojo.
5. Refer to the features below for a full list of commands.

---

## Features

### 1. Add a Todo – `todo`
Adds a simple task with no date or time attached.

**Format:** `todo DESCRIPTION`

**Example:**
```
todo borrow book
```
```
Got it. I've added this task:
  [T][ ] borrow book
Now you have 1 tasks in the list.
```

---

### 2. Add a Deadline – `deadline`
Adds a task that needs to be completed by a specific date/time.

**Format:** `deadline DESCRIPTION /by DATE`

- DATE can be a plain string (e.g. `Sunday`) or in `yyyy-MM-dd HHmm` format (e.g. `2019-10-15 1800`) for automatic formatting.

**Example:**
```
deadline return book /by 2019-10-15 1800
```
```
Got it. I've added this task:
  [D][ ] return book (by: Oct 15 2019, 6:00pm)
Now you have 2 tasks in the list.
```

---

### 3. Add an Event – `event`
Adds a task that starts and ends at specific times.

**Format:** `event DESCRIPTION /from START /to END`

**Example:**
```
event project meeting /from Mon 2pm /to 4pm
```
```
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
```

---

### 4. List All Tasks – `list`
Displays all tasks currently in your list. If your list is empty, Gojo will let you know.

**Format:** `list`

**Example (with tasks):**
```
list
```
```
1.[T][ ] borrow book
2.[D][ ] return book (by: Oct 15 2019, 6:00pm)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
```

**Example (empty list):**
```
list
```
```
Hah, there's nothing here. You haven't given me a single task yet. Boring.
```


---

### 5. Mark a Task as Done – `mark`
Marks a task as completed.

**Format:** `mark INDEX`

**Example:**
```
mark 1
```
```
Good. I've marked this task as done:
  [T][X] borrow book
```

---

### 6. Unmark a Task – `unmark`
Marks a task as not done.

**Format:** `unmark INDEX`

**Example:**
```
unmark 1
```
```
Okay, I've marked this task as not done:
  [T][ ] borrow book
```

---

### 7. Delete a Task – `delete`
Removes a task from the list permanently.

**Format:** `delete INDEX`

**Example:**
```
delete 1
```
```
Noted. I've removed this task:
  [T][ ] borrow book
Now you have 2 tasks in the list.
```

---

### 8. Find Tasks by Keyword – `find`
Searches for tasks whose description contains the given keyword.

**Format:** `find KEYWORD`

**Example:**
```
find book
```
```
Here are the matching tasks in your list:
1.[T][ ] borrow book
2.[D][ ] return book (by: Oct 15 2019, 6:00pm)
```

---

### 9. Exit – `bye`
Exits the Gojo chatbot. Your tasks are automatically saved and will be reloaded the next time you start the app.

**Format:** `bye`

**Example:**
```
bye
```
```
Bye. Don't get weak while I'm gone.
```

---

## Command Summary

| Command | Format | Example |
|---------|--------|---------|
| todo | `todo DESCRIPTION` | `todo borrow book` |
| deadline | `deadline DESCRIPTION /by DATE` | `deadline return book /by 2019-10-15 1800` |
| event | `event DESCRIPTION /from START /to END` | `event meeting /from Mon 2pm /to 4pm` |
| list | `list` | `list` |
| mark | `mark INDEX` | `mark 1` |
| unmark | `unmark INDEX` | `unmark 1` |
| delete | `delete INDEX` | `delete 2` |
| find | `find KEYWORD` | `find book` |
| bye | `bye` | `bye` |

---

## Data Storage
Tasks are automatically saved to `data/gojo.txt` after every command. There is no need to save manually. The file is loaded automatically when Gojo starts up.