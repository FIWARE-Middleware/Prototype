class CalculatorServantExample extends CalculatorServant
{
    public int add(int param1, int param2)
    {
        System.out.println("Adding " + param1 + " and " + param2);
        return param1 + param2;
    }

    public int subtract(int param1, int param2)
    {
        System.out.println("Subtracting " + param1 + " and " + param2);
        return param1 - param2;
    }
}
