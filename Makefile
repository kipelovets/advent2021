run:
	rm -f build/*
	yarn tsc
	ls build/*.js | xargs -n1 node 

last:
	rm -f build/*
	yarn tsc
	node build/day6_1.js
