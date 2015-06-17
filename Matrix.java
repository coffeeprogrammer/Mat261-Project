
public class Matrix
{
    Matrix(int row, int column)
    {
        myArray = new double[row][column];
    }

    Matrix(double a[][])
    {
        myArray = a;
        System.out.println(myArray[0][0]);
    }

    public Matrix multiply(Matrix matrix)
    {
        //myArray[0].length == matrix.myArray.length;
        if(this.getColumnCount() == matrix.getRowCount())
        {
            double elementAccumulator;
            Matrix resultMatrix = new Matrix(this.getRowCount(), matrix.getColumnCount());
            for (int j = 0; j < this.getRowCount(); j++)
            {
                for( int k = 0; k < matrix.getColumnCount(); k++)
                {
                    elementAccumulator = 0;
                    for(int l = 0; l < this.getColumnCount(); l++)
                    {
                        elementAccumulator += myArray[j][l] * matrix.myArray[l][k];
                    } // end of l loop
                    resultMatrix.myArray[j][k] = elementAccumulator;
                } // end of k loop
            } // end of j loop
            return resultMatrix;
        }

        return null;
    }

    public int getRowCount()
    {
        return myArray.length;
    }

    public int getColumnCount()
    {
        return myArray[0].length;
    }

    public double get(int row, int column)
    {
        return myArray[row][column];
    }

    private double myArray[][];
}
