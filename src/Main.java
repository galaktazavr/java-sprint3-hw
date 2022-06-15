import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();

        printMenu();
        String userInputMenu = scanner.nextLine();
        while (!userInputMenu.equals("#$rt")) {
            switch (userInputMenu) {
                case "1":
                    printTasksOfChosenType(chooseTypeOfTask(scanner), taskManager);
                    break;
                case "2":
                    removeAllTasksOfChosenType(chooseTypeOfTask(scanner), taskManager);
                    break;
                case "3":
                    printCertainTasksOfChosenType(chooseTypeOfTask(scanner), taskManager, scanner);
                    break;
                case "4":
                    addNewTask(chooseTypeOfTask(scanner), taskManager, scanner);
                    break;
                case "5":
                    changeCertainTaskOfChosenType(chooseTypeOfTask(scanner), taskManager, scanner);
                    break;
                case "6":
                    removeCertainTasksOfChosenType(chooseTypeOfTask(scanner), taskManager, scanner);
                    break;
                case "7":
                    printAllTasksByChosenEpic(taskManager, scanner);
                    break;
                default:
                    System.out.println("Вы ввели несуществующий пункт программы");
            }
            printMenu();
            userInputMenu = scanner.nextLine();
        }
        System.out.println("Программа завершает свою работу.");
    }

    private static void printAllTasksByChosenEpic(TaskManager taskManager, Scanner scanner) {
        System.out.println("Введите id эпика, список задач которого хотите поменять");
        int id = Integer.parseInt(scanner.nextLine());
        if (taskManager.epicList.containsKey(id)) {
            System.out.println(taskManager.epicList.get(id).subtaskId);
        } else {
            System.out.println("Эпика с таким id не существует");
        }
    }

    private static void changeCertainTaskOfChosenType(int typeOfTask, TaskManager taskManager, Scanner scanner) {
        System.out.println("Введите id задачи, содержание которой необходимо поменять");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Введите новое название задачи");
        String name = scanner.nextLine();
        System.out.println("Введите новое описание задачи");
        String description = scanner.nextLine();

        if (typeOfTask == 1) {
            if (taskManager.taskList.containsKey(id)) {
                taskManager.taskList.put(id, new Task(name, description, id, chooseNewTaskStatusForTasks(scanner)));
            } else {
                System.out.println("Задачи с таким номером не существует");
            }
        } else if (typeOfTask == 2) {
            int idEpic = taskManager.subtaskList.get(id).epicId;
            if (taskManager.epicList.containsKey(idEpic)) {
                taskManager.subtaskList.put(id, new Subtask(name, description, id,
                        chooseNewTaskStatusForTasks(scanner), idEpic));
                taskManager.epicList.get(idEpic).status = checkEpicStatus(taskManager, idEpic);
            } else {
                System.out.println("Задачи с таким номером не существует");
            }
        } else if (typeOfTask == 3) {
            if (taskManager.epicList.containsKey(id)) {
                taskManager.epicList.put(id, new Epic(name, description, id,
                        checkEpicStatus(taskManager, id)));
            } else {
                System.out.println("Задачи с таким номером не существует");
            }
        }
    }

    public static String checkEpicStatus(TaskManager taskManager, int id) {
        String status;
        if (taskManager.epicList.get(id).subtaskId == null) {
            status = "NEW";
        } else {
            int collectNew = 0;
            int collectInProgress = 0;
            int collectDone = 0;
            for (Integer taskId : taskManager.epicList.get(id).subtaskId) {
                if (taskManager.subtaskList.get(taskId).status.equals("NEW")) {
                    collectNew++;
                } else if (taskManager.subtaskList.get(taskId).status.equals("IN_PROGRESS")) {
                    collectInProgress++;
                } else if (taskManager.subtaskList.get(taskId).status.equals("DONE")) {
                    collectDone++;
                }
            }
            if ((collectInProgress > 0) || ((collectInProgress == 0) && (collectDone > 0) && (collectNew > 0))) {
                return "IN_PROGRESS";
            } else if ((collectInProgress == 0) && (collectNew == 0) && (collectDone > 0)) {
                return "DONE";
            } else {
                return "NEW";
            }
        }
        return status;
    }

    public static String chooseNewTaskStatusForTasks(Scanner scanner) {
        System.out.println("Введите новый статус задачи");
        System.out.println("1 - NEW");
        System.out.println("2 - IN_PROGRESS");
        System.out.println("3 - DONE");
        String newTaskStatus;
        int status = Integer.parseInt(scanner.nextLine());
        while (true) {
            if (status == 1) {
                newTaskStatus = "NEW";
                break;
            } else if (status == 2) {
                newTaskStatus = "IN_PROGRESS";
                break;
            } else if (status == 3) {
                newTaskStatus = "DONE";
                break;
            } else {
                System.out.println("Вы ввели неверное значение, повторите операцию");
                System.out.println("Введите новый статус задачи");
                System.out.println("1 - NEW");
                System.out.println("2 - IN_PROGRESS");
                System.out.println("3 - DONE");
                status = Integer.parseInt(scanner.nextLine());
            }
        }
        return newTaskStatus;
    }

    private static void printCertainTasksOfChosenType(int typeOfTask, TaskManager taskManager, Scanner scanner) {
        System.out.println("Введите id задачи, содержание которой необходимо показать");
        int id = Integer.parseInt(scanner.nextLine());
        if (typeOfTask == 1) {
            if (taskManager.taskList.containsKey(id)) {
                System.out.println(taskManager.taskList.get(id));
            } else {
                System.out.println("Задачи с таким номером не существует");
            }
        } else if (typeOfTask == 2) {
            if (taskManager.subtaskList.containsKey(id)) {
                System.out.println(taskManager.subtaskList.get(id));
            } else {
                System.out.println("Задачи с таким номером не существует");
            }
        } else if (typeOfTask == 3) {
            if (taskManager.epicList.containsKey(id)) {
                System.out.println(taskManager.epicList.get(id));
            } else {
                System.out.println("Задачи с таким номером не существует");
            }
        }
    }

    private static void removeCertainTasksOfChosenType(int typeOfTask, TaskManager taskManager, Scanner scanner) {
        System.out.println("Введите id задачи, которую необходимо удалить");
        int id = Integer.parseInt(scanner.nextLine());
        if (typeOfTask == 1) {
            if (taskManager.taskList.containsKey(id)) {
                taskManager.taskList.remove(id);
            } else {
                System.out.println("Задачи с таким номером не существует");
            }
        } else if (typeOfTask == 2) {
            if (taskManager.subtaskList.containsKey(id)) {
                taskManager.subtaskList.remove(id);
            } else {
                System.out.println("Задачи с таким номером не существует");
            }
        } else if (typeOfTask == 3) {
            if (taskManager.epicList.containsKey(id)) {
                taskManager.epicList.remove(id);
            } else {
                System.out.println("Задачи с таким номером не существует");
            }
        }
    }

    private static void removeAllTasksOfChosenType(int typeOfTask, TaskManager taskManager) {
        if (typeOfTask == 1) {
            taskManager.taskList.clear();
        } else if (typeOfTask == 2) {
            taskManager.subtaskList.clear();
        } else if (typeOfTask == 3) {
            taskManager.epicList.clear();
        }
    }

    private static void printTasksOfChosenType(int typeOfTask, TaskManager taskManager) {
        if (typeOfTask == 1) {
            System.out.println(taskManager.taskList);
        } else if (typeOfTask == 2) {
            System.out.println(taskManager.subtaskList);
        } else if (typeOfTask == 3) {
            System.out.println(taskManager.epicList);
        }
    }

    private static void addNewTask(int typeOfTask, TaskManager taskManager, Scanner scanner) {
        System.out.println("Введите название задачи");
        String name = scanner.nextLine();
        System.out.println("Введите описание задачи");
        String description = scanner.nextLine();
        if (typeOfTask == 1) {
            taskManager.taskList.put(taskManager.id, new Task(name, description, taskManager.id, "NEW"));
            taskManager.id++;
        } else if (typeOfTask == 2) {
            System.out.println("Выберите id эпика к которому должна принадлежать подзадача");
            int idEpic = Integer.parseInt(scanner.nextLine());
            if (taskManager.epicList.containsKey(idEpic)) {
                taskManager.subtaskList.put(taskManager.id, new Subtask(name, description, taskManager.id, "NEW",
                        idEpic));
                taskManager.epicList.get(idEpic).subtaskId.add(taskManager.id);
                taskManager.id++;
                taskManager.epicList.get(idEpic).status = checkEpicStatus(taskManager, idEpic);
            } else {
                System.out.println("Такого эпика не существует. Подзадача не может быть создана в отрыве от эпика.");
            }
        } else if (typeOfTask == 3) {
            taskManager.epicList.put(taskManager.id, new Epic(name, description, taskManager.id, "NEW"));
            taskManager.id++;
        }
    }

    private static int chooseTypeOfTask(Scanner scanner) {
        printChooseTypeOfTask();
        String userInputTask = scanner.nextLine();
        while (!userInputTask.equals("#$rt")) {
            switch (userInputTask) {
                case "1":
                    return 1;
                case "2":
                    return 2;
                case "3":
                    return 3;
                default:
                    System.out.println("Вы ввели несуществующий пункт программы");
            }
            printChooseTypeOfTask();
            userInputTask = scanner.nextLine();
        }
        return 0;
    }

    private static void printMenu() {
        System.out.println("Выберите функцию программы:");
        System.out.println("1 - Получить список всех задач по типу");
        System.out.println("2 - Удалить все задачи по типу");
        System.out.println("3 - Получить задачу по id");
        System.out.println("4 - Создать задачу");
        System.out.println("5 - Обновить задачу");
        System.out.println("6 - Удаление по идентификатору");
        System.out.println("7 - Получение списка подзадач определенного эпика");
        System.out.println("#$rt - Завершение программы");
    }

    private static void printChooseTypeOfTask() {
        System.out.println("Выберите тип задача:");
        System.out.println("1 - Task");
        System.out.println("2 - Subtask");
        System.out.println("3 - Epic");
        System.out.println("#$rt - Завершение программы");
    }
}
