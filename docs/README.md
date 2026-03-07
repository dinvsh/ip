# Steve, Your Friendly Task Manager Chatbot

Steve is a command-line chatbot that helps you track your tasks. Tell Steve what you need to do, and he'll remember it so you don't have to.

---

## Quick Start

1. Ensure you have **Java 17** or later installed.
2. Download the latest `steve.jar` from the [Releases](../../releases) page.
3. Run Steve from your terminal:
   ```
   java -jar steve.jar
   ```
4. Start typing commands. Steve saves your tasks automatically to `data/steve.txt`.

---

## Features

### Add a Todo
A simple task with no date attached.

**Format:** `todo DESCRIPTION`

**Example:**
```
todo buy groceries
```

---

### Add a Deadline
A task that must be done by a specific date.

**Format:** `deadline DESCRIPTION /by YYYY-MM-DD`

**Example:**
```
deadline submit assignment /by 2025-12-01
```

---

### Add an Event
A task that spans a date range.

**Format:** `event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD`

**Example:**
```
event team retreat /from 2025-11-10 /to 2025-11-12
```

---

### List All Tasks
See everything currently in your list.

**Format:** `list`

**Example output:**
```
 here's your stuff cuh:
 1.[T][ ] buy groceries
 2.[D][ ] submit assignment (by: Dec 01 2025)
 3.[E][ ] team retreat (from: Nov 10 2025 to: Nov 12 2025)
```

**Task prefixes explained:**
| Prefix | Meaning |
|--------|---------|
| `[T]` | Todo |
| `[D]` | Deadline |
| `[E]` | Event |
| `[X]` | Done |
| `[ ]` | Not done |

---

### Mark a Task as Done
**Format:** `mark INDEX`

**Example:** `mark 2`

---

### Unmark a Task
**Format:** `unmark INDEX`

**Example:** `unmark 2`

---

### Delete a Task
**Format:** `delete INDEX`

**Example:** `delete 3`

> **Note:** INDEX refers to the number shown next to the task in the `list` view.

---

### Find Tasks by Keyword
Search for tasks whose descriptions contain a specific word or phrase.

**Format:** `find KEYWORD`

**Example:** `find assignment`

---

### Exit
**Format:** `bye`

Steve will save your tasks and say goodbye.

---

## Date Format

All dates must be entered in **`YYYY-MM-DD`** format (e.g., `2025-12-31`).  
Steve will display them in a friendlier format like `Dec 31 2025`.

---

## Data Storage

Your tasks are automatically saved to `data/steve.txt` in the same directory where you run Steve. This file is created automatically — you don't need to do anything. If the file is missing or corrupted, Steve starts fresh with an empty list.

---

## Command Summary

| Command | Format |
|---------|--------|
| Add todo | `todo DESCRIPTION` |
| Add deadline | `deadline DESCRIPTION /by YYYY-MM-DD` |
| Add event | `event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD` |
| List tasks | `list` |
| Mark done | `mark INDEX` |
| Unmark | `unmark INDEX` |
| Delete | `delete INDEX` |
| Find | `find KEYWORD` |
| Exit | `bye` |