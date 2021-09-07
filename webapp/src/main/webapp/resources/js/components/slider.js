// source: https://dev.to/davidcolbyatx/building-a-horizontal-slider-with-stimulus-and-tailwind-css-1fjp
(() => {
    const application = Stimulus.Application.start()

    application.register("slider", class extends Stimulus.Controller {
        static get targets() {
            return ["scrollContainer", "image", "indicator"]
        }

        initialize() {
            this.observer = new IntersectionObserver(this.onIntersectionObserved.bind(this), {
                root: this.scrollContainerTarget,
                threshold: 0.5
            })
            this.imageTargets.forEach(image => {
                this.observer.observe(image)
            })
        }

        onIntersectionObserved(entries) {
            entries.forEach(entry => {
                if (entry.intersectionRatio > 0.5) {
                    const intersectingIndex = this.imageTargets.indexOf(entry.target)
                    this.indicatorTargets[intersectingIndex].classList.add("bg-purple-900")
                } else {
                    const intersectingIndex = this.imageTargets.indexOf(entry.target)
                    this.indicatorTargets[intersectingIndex].classList.remove("bg-purple-900")
                }
            })
        }

        scrollTo() {
            const imageId = event.target.dataset.imageId
            const imageElement = document.getElementById(imageId)
            println(imageId);
            imageElement.scrollIntoView({ block: "end", inline: "nearest", behavior: "smooth" })
        }
        aux(){
            println(event.target.dataset.imageId)
        }

    })
})()
