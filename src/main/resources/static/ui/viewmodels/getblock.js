var app = new Vue({
    el: '#app',
    data: {
        blockheight: '',
        pageinfo: [],
        hash: '',
    },
    mounted() {
        var url = new URL(location.href);
        this.blockheight = url.searchParams.get("height");
        this.hash = url.searchParams.get("blockhash");
        console.log(this.blockheight);
        console.log(this.hash);
        if (this.blockheight != null) {
            this.getblockbyheight();
        } else if (this.hash != null) {
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
                    hash: this.hash
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