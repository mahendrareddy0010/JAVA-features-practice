Below has the times to resize the array when it is full.
Initially, it is `10`, then it is increment to `oldCapacity + oldCapacity >> 1`
`Eg: `10, 15, 22, 33,.......
`Observation : ` when resizing is happening, it take more time, otherwise it taks only `1 or 2 micro seconds`

![alt text](<Screenshot from 2024-04-22 18-19-03.png>)