var app = new Vue({
    el: '#app',
    data: {
        pageinfo:[]
    },
    computed:{
        // showRecentBlocks(){
        //     var now = Date.now();
        //     this.pageinfo.forEach(block => {
        //         block.showtime = parseInt((now - block.time)/1000/60);
        //         block.showSizeOnDisk = block.sizeOnDisk.toLocaleString('en');
        //     });
        //     return this.pageinfo;
        // }
    },
    mounted() {
        this.getblocklist();
    },
    methods: {
        getblocklist() {
            axios.get('http://localhost:8080/block/getRecentBlocks')
                .then(function (response) {
                    
                    app.pageinfo = response.data.list;
                    
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        togetblock(block){
            console.log(block);
            location.href='getblock.html?height='+block.height;
        }

    }
})