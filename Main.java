import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class Process {
    String processID;
    int arrivalTime;
    int burstTime;
    int waitingTime;
    int turnaroundTime;

    Process(String pid) {
        processID = pid;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean newSet = true;
        while (newSet) {
            // Step 1: Input number of processes
            int n = 0;
            boolean validN = false;
            while (!validN) {
                System.out.print("Enter the no. of process (3-10): ");
                String input = sc.next();

                boolean isNumeric = input.chars().allMatch(Character::isDigit);
                if (isNumeric) {
                    n = Integer.parseInt(input);
                    if (n >= 3 && n <= 10) {
                        validN = true;
                    } else {
                        System.out.println("Invalid input. Please enter a number between 3 and 10.");
                    }
                } else {
                    System.out.println("Invalid input. Only positive integers are allowed.");
                }
            }

            // Step 2: Input Process IDs
            Process[] processes = new Process[n];
            Set<String> usedProcessIDs = new HashSet<>();
            for (int i = 0; i < n; i++) {
                boolean validPID = false;
                while (!validPID) {
                    System.out.print("Enter process ID for Process " + (i + 1) + ": ");
                    String pid = sc.next().trim();
                    if (pid.isEmpty()) {
                        System.out.println("Invalid input. Process ID cannot be empty.");
                    } else if (usedProcessIDs.contains(pid)) {
                        System.out.println("Invalid input. Process ID must be unique.");
                    } else {
                        processes[i] = new Process(pid);
                        usedProcessIDs.add(pid);
                        validPID = true;
                    }
                }
            }

            // Step 3: Input Arrival Times
            Set<Integer> usedArrivalTimes = new HashSet<>();
            for (int i = 0; i < n; i++) {
                boolean validAT = false;
                while (!validAT) {
                    System.out.print("Enter arrival time for " + processes[i].processID + ": ");
                    if (sc.hasNextInt()) {
                        int at = sc.nextInt();
                        if (at < 0) {
                            System.out.println("Invalid input. Arrival time must be 0 or greater.");
                        } else if (usedArrivalTimes.contains(at)) {
                            System.out.println("Invalid input. Arrival time must not be repeated.");
                        } else {
                            processes[i].arrivalTime = at;
                            usedArrivalTimes.add(at);
                            validAT = true;
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a valid integer.");
                        sc.next();
                    }
                }
            }

            // Step 4: Input Burst Times
            for (int i = 0; i < n; i++) {
                boolean validBT = false;
                while (!validBT) {
                    System.out.print("Enter burst time for " + processes[i].processID + ": ");
                    if (sc.hasNextInt()) {
                        int bt = sc.nextInt();
                        if (bt <= 0) {
                            System.out.println("Invalid input. Burst time must be a positive integer.");
                        } else {
                            processes[i].burstTime = bt;
                            validBT = true;
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a valid integer.");
                        sc.next();
                    }
                }
            }

            // Step 5: Compute Waiting and Turnaround Times (FCFS)
            int totalWT = 0, totalTAT = 0;
            int currentTime = 0;

            java.util.Arrays.sort(processes, (a, b) -> a.arrivalTime - b.arrivalTime);

            for (Process p : processes) {
                int startTime = Math.max(currentTime, p.arrivalTime);
                int completionTime = startTime + p.burstTime;

                p.turnaroundTime = completionTime - p.arrivalTime;
                p.waitingTime = p.turnaroundTime - p.burstTime;

                totalWT += p.waitingTime;
                totalTAT += p.turnaroundTime;

                currentTime = completionTime;
            }

            // Step 6: Display Process Table
            System.out.printf("\n%-10s %-15s %-15s %-15s\n",
                    "Process", "Arrival Time", "Burst Time", "Turnaround Time");
            for (Process p : processes) {
                System.out.printf("%-10s %-15d %-15d %-15d\n",
                        p.processID, p.arrivalTime, p.burstTime, p.turnaroundTime);
            }

            // Step 7: Display Averages
            System.out.println("\nAverage arrival time: %.1f" + (float) totalWT / n);
            System.out.println("Average turn-around time: %.1f" + (float) totalTAT / n);

            // Step 8: Ask user if they want another set
            System.out.print("\nDo you want to enter a new set of processes? (y/n): ");
            String again = sc.next();
            newSet = again.equalsIgnoreCase("y");
        }

        sc.close();
    }
}
