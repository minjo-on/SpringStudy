package chap07;

public class ExeTimeCalculator implements Calculator{
    private Calculator delegate;
    public ExeTimeCalculator(Calculator delegate){
        this.delegate=delegate;
    }
    @Override
    public long factorial(long num) {
        long start = System.nanoTime();
        long result = delegate.factorial(num);
        long end = System.nanoTime();
        System.out.println("ImpeCalculator.factorial("+num+") 실행시간 = "+(end-start));
        return result;
    }
}
