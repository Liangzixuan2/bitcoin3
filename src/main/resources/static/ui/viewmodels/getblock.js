var app = new Vue({
    el: '#app',
    data: {
        blockheight: '',
        pageinfo: '',
        blockhash: ''
    },
    mounted() {
        var url = new URL(location.href);
        this.blockheight = url.searchParams.get("height");
        this.blockhash = url.searchParams.get("blockhash");
        console.log(this.blockheight);
        console.log(this.blockhash);
        if (this.blockheight != null) {
            this.getblockbyheight();
        } else if (this.blockhash != null) {
            this.getblockbyhash();
        }

    },
    methods: {
        getblockbyheight() {
            axios.get('http://localhost:8080/block/getBlockDetailByHeight', {
                params: {
                    blockheight: this.blockheight
                }
            })
                .then(function (response) {
                    console.log(response);
                    app.pageinfo = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        getblockbyhash() {
            axios.get('http://localhost:8080/block/getBlockDetailByHash', {
                params: {
                    blockhash: this.blockhash
                }
            })
                .then(function (response) {
                    console.log(response);
                    app.pageinfo = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    }
})