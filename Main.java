import java.util.Scanner;

class Process {
    String processID;
    int waitingTime;
    int burstTime;
    int turnaroundTime;

    Process(String pid) {
        processID = pid;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Step 1: Input number of processes
        System.out.print("Enter the no. of process: ");
        int n = sc.nextInt();

        while (n < 3 || n > 10) {
            System.out.println("Invalid input. Please enter between 3 and 10.");
            n = sc.nextInt();
        }

        // Step 2: Input Process IDs
        Process[] processes = new Process[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter process ID for Process" + (i + 1) + ": ");
            String pid = sc.next();
            processes[i] = new Process(pid);
        }

        // Step 3: Input Waiting Times
        for (int i = 0; i < n; i++) {
            System.out.print("Enter waiting time for " + processes[i].processID + ": ");
            processes[i].waitingTime = sc.nextInt();
        }

        // Step 4: Input Burst Times
        for (int i = 0; i < n; i++) {
            System.out.print("Enter burst time for " + processes[i].processID + ": ");
            processes[i].burstTime = sc.nextInt();
        }

        // Step 5: Compute Turnaround Times
        int totalWT = 0, totalTAT = 0;
        for (int i = 0; i < n; i++) {
            processes[i].turnaroundTime = processes[i].waitingTime + processes[i].burstTime;
            totalWT += processes[i].waitingTime;
            totalTAT += processes[i].turnaroundTime;
        }

        // Step 6: Display Process Table
        System.out.println("\nProcess\tWaiting Time\tBurst Time\tTurnaround Time");
        for (Process p : processes) {
            System.out.println(p.processID + "\t" + p.waitingTime + "\t\t" + p.burstTime + "\t\t" + p.turnaroundTime);
        }

        // Step 7: Display Averages
        System.out.println("\nAverage waiting: " + (float) totalWT / n);
        System.out.println("Average turn-around time: " + (float) totalTAT / n);

        sc.close();
    }
}