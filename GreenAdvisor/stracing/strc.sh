while ! ps  | grep $1 ; do :; done;
ps | grep $1 | while read a b c; do
        strace -f -c  -p $b -o /data/local/trc.txt
       #kill $b;
done;


