run:
	rm -f build/*
	yarn tsc
	ls build/*.js | xargs -n1 node 
