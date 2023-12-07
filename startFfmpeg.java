public class startFfmpeg {
    public startFfmpeg() {
        try {
            // comand to be executed
            String command = "ffmpeg -i input.mp4 -c:v libx264 -c:a aac output.mp4"

            // create and run process
            ProcessBuilder pb = new ProcessBuilder(command.split(" "));
            pb.redirectErrorStream(true);

            Process process = pb.start();

            process.waitFor();
        } catch (IOException | Interrupted Exception ex) {
            ex.printStacktrace();
        }
    }
    public static void main (String[] args){
    }

}
