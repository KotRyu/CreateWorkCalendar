package app;

/**
 * Constants
 */
public class Constants {

    //仕事の曜日
    public static enum WorkDayOfWeek{
        Monday(2),
        Tuesday(3),
        Wednesday(4),
        Thursday(5),
        Friday(6);

        private int youbi;

        private WorkDayOfWeek(int youbi){
            this.youbi = youbi;
        }

        public int toInt(){
            return youbi;
        }

    }
}

