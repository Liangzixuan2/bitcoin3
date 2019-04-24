var app = new Vue({
    el: '#app',
    data: {
        blockheight: '',
        pageinfo: ''
    },
    mounted() {
        var url = new URL(location.href);
        this.blockheight = url.searchParams.get("height");
        this.getblock();
    },
    methods: {
        getblock() {
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
        }
    }
})