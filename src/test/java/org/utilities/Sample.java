package org.utilities;

public class Sample {
    public static void reverseNumber()
    {
        int number=987654,rev=0;
        while (number!=0)
        {
            int reim=number%10;
            rev=rev*10+reim;
            number=number/10;
        }
        System.out.println(rev);
    }
    public static void isPalindrome(int n)
    {
        int r,sum=0,temp;
        temp=n;
        while (n>0)
        {
            r=n%10; //getting remainder
            sum=(sum*10)+r;
            n=n/10;
        }
        if (temp==sum)
            System.out.println("palindrome number : " + sum);
        else
            System.out.println("not palindrome : "+sum);
    }

    public static void reverseString()
    {
        String str="Vaibhav";
        String rev="";
        int len=str.length()-1;
        while (len>=0)
        {
            rev+=(str.charAt(len));
            len --;
        }
        System.out.println(rev);
    }
    public static void reverseString1()
    {
        String input="vaibhav";
        char[] tryArray = input.toCharArray();
        System.out.println(tryArray.length-1);
        System.out.println(tryArray.length);
        for (int i=tryArray.length-1;i>=0;i--)
        {
            System.out.print(tryArray[i]);
        }
    }
    public static void reverseString2()
    {
        String temp,inp="Garse";

    }
    public static void StringClass()
    {
        String s1="i am vaibhav";
        String s2="i am vaibhav";
        System.out.println(s1==s2);
    }
    public static void main(String[] args) {
        //reverseNumber();
        //reverseString();
        //isPalindrome(124);
        //reverseString1();
        StringClass();
    }
}
//456789