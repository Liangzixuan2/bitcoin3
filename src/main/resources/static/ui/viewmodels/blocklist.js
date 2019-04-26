var app = new Vue({
    el: '#app',
    data: {
        pageinfo:[],
        input:''
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
                    
                    app.pageinfo = response.data;
                    
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        togetblock(block){
            console.log(block);
            location.href='getblock.html?height='+block.height;
        },

        sousuo(){
            console.log(this.input.length);
            if (this.input.length==64 && this.input.indexOf("0000000") != -1) {
                location.href='getblock.html?blockhash='+this.input;
            } else if(this.input.length==64) {
                location.href='gettransaction.html?txhash='+this.input;
            }else if(this.input.length<64) {
                
                location.href='gettransaction_detail.html?address='+this.input;
            }
        }

    }
})