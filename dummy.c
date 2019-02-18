int foo(int a, int b){
  return a+b;
}

int main(void){
  int a=1, b=2, c, i;
  c=foo(a,b);
  for(i=0; i<5; i=i+1){
    c=c+i;
  }
  printf("c=%d\n", c);
  return 0;
}
