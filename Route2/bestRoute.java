// algorithm for determining best route between location A and B

public class BestRoute {

    public static List<Pair<Integer, Integer>> calculateBestRoute(double[] greatHall, double[] nightMarket) {
        // Get the actual distances between the two locations.
        double[][] distances = GoogleMaps.getDistances(locationA, locationB

        // Initialize the transportation table.
        int[][] transportationTable = new int[2][2];

        // Fill the transportation table using the Vogel Approximation Method.
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (distances[i][j] < 0) {
                    transportationTable[i][j] = (int) distances[i][j];
                } else {
                    transportationTable[i][j] = 0;
                }
            }
        }

        // Find the best route in terms of the distance.
        List<Pair<Integer, Integer>> bestRoute = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (transportationTable[i][j] > 0) {
                    bestRoute.add(new Pair<>(i, j));
                }
            }
        }

        return bestRoute;
    }

    public static void main(String[] args) {
        double[] locationA= new double[]{5.651866330036329, -0.19624350189956294};
        double[] locationB= new double[]{5.642019147257913, -0.18575957561817277};

        List<Pair<Integer, Integer>> bestRoute = calculateBestRoute(locationA, locationB);
        System.out.println(bestRoute);
    }
}
