document.addEventListener('DOMContentLoaded', () => {
    const slide = document.querySelector('.carousel-slide');
    const dots = document.querySelectorAll('.dot');
    let currentIndex = 0;
    let slideCount = dots.length;
    let intervalId = null;
  
    function showSlide(index) {
      slide.style.transform = `translateX(-${index * 100}%)`;
      dots.forEach(dot => dot.classList.remove('active'));
      dots[index].classList.add('active');
      currentIndex = index;
    }
  
    function nextSlide() {
      const nextIndex = (currentIndex + 1) % slideCount;
      showSlide(nextIndex);
    }
  
    function startAutoSlide() {
      intervalId = setInterval(nextSlide, 5000);
    }
  
    function resetAutoSlide() {
      clearInterval(intervalId);
      startAutoSlide();
    }
  
    dots.forEach(dot => {
      dot.addEventListener('click', () => {
        const index = parseInt(dot.getAttribute('data-index'));
        showSlide(index);
        resetAutoSlide();
      });
    });
  
    startAutoSlide();
  });
  